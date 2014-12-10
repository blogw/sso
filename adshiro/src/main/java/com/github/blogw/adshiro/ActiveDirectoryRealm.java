package com.github.blogw.adshiro;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.ldap.AbstractLdapRealm;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.apache.shiro.realm.ldap.LdapUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveDirectoryRealm
		extends AbstractLdapRealm
{
	private static final Logger log = LoggerFactory.getLogger(ActiveDirectoryRealm.class);
	private static final String ROLE_NAMES_DELIMETER = ",";
	private Map<String, String> groupRolesMap;

	public ActiveDirectoryRealm() {
	}

	public void setGroupRolesMap(Map<String, String> groupRolesMap)
	{
		this.groupRolesMap = groupRolesMap;
	}

	protected AuthenticationInfo queryForAuthenticationInfo(AuthenticationToken token,
			LdapContextFactory ldapContextFactory)
			throws NamingException
	{
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		LdapContext ctx = null;
		Object principal = token.getPrincipal();  
        Object credentials = token.getCredentials();  
        
		try
		{
			ctx = ldapContextFactory.getLdapContext(principal, credentials);
		} finally
		{
			LdapUtils.closeContext(ctx);
		}
		return buildAuthenticationInfo(upToken.getUsername(), upToken.getPassword());
	}

	protected AuthenticationInfo buildAuthenticationInfo(String username, char[] password)
	{
		return new SimpleAuthenticationInfo(username, password, getName());
	}

	protected AuthorizationInfo queryForAuthorizationInfo(PrincipalCollection principals,
			LdapContextFactory ldapContextFactory)
			throws NamingException
	{
		String username = (String) getAvailablePrincipal(principals);

		LdapContext ldapContext = ldapContextFactory.getSystemLdapContext();
		Set<String> roleNames;
		try
		{
			roleNames = getRoleNamesForUser(username, ldapContext);
			int i=0;
			System.out.println(i);
			
		} finally
		{
			LdapUtils.closeContext(ldapContext);
		}
		return buildAuthorizationInfo(roleNames);
	}

	protected AuthorizationInfo buildAuthorizationInfo(Set<String> roleNames)
	{
		return new SimpleAuthorizationInfo(roleNames);
	}

	private Set<String> getRoleNamesForUser(String username, LdapContext ldapContext)
			throws NamingException
	{
		Set<String> roleNames = new LinkedHashSet();

		SearchControls searchCtls = new SearchControls();
		searchCtls.setSearchScope(2);

		String userPrincipalName = username;
		if (this.principalSuffix != null) {
			userPrincipalName = userPrincipalName + this.principalSuffix;
		}
		String searchFilter = "(&(objectClass=*)(userPrincipalName={0}))";
		Object[] searchArguments = { userPrincipalName };

		NamingEnumeration answer = ldapContext.search(this.searchBase, searchFilter, searchArguments, searchCtls);
		while (answer.hasMoreElements())
		{
			SearchResult sr = (SearchResult) answer.next();
			if (log.isDebugEnabled()) {
				log.debug("Retrieving group names for user [" + sr.getName() + "]");
			}
			Attributes attrs = sr.getAttributes();
			if (attrs != null)
			{
				NamingEnumeration ae = attrs.getAll();
				while (ae.hasMore())
				{
					Attribute attr = (Attribute) ae.next();
//					System.out.println(attr.getID());
//					Collection<String> gg = LdapUtils.getAllAttributeValues(attr);
//					System.out.println("Groups found for user [" + username + "]: " + gg);
					
					if (attr.getID().equals("memberOf"))
					{
						Collection<String> groupNames = LdapUtils.getAllAttributeValues(attr);
						System.out.println("Groups found for user [" + username + "]: " + groupNames);
						
						Collection<String> gs=new ArrayList<String>();
						for(String s:groupNames){
							String[] group=s.split(",");
							for(String g:group){
								if(g.startsWith("CN=")){
									gs.add(g.substring(3));
								}
							}
						}
						
						Collection<String> rolesForGroups = getRoleNamesForGroups(gs);
						roleNames.addAll(rolesForGroups);
					}
				}
			}
		}
		return roleNames;
	}

	protected Collection<String> getRoleNamesForGroups(Collection<String> groupNames)
	{
		Set<String> roleNames = new HashSet(groupNames.size());
		if (this.groupRolesMap != null) {
			for (String groupName : groupNames)
			{
				String strRoleNames = (String) this.groupRolesMap.get(groupName);
				
				log.info(strRoleNames);
				if (strRoleNames != null) {
					for (String roleName : strRoleNames.split(","))
					{
						if (log.isDebugEnabled()) {
							log.debug("User is member of group [" + groupName + "] so adding role [" + roleName + "]");
						}
						roleNames.add(roleName);
					}
				}
			}
		}
		return roleNames;
	}
}