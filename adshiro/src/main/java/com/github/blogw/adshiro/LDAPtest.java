package com.github.blogw.adshiro;

/**
*
* @author icuit
*/
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class LDAPtest {

	public static void main(String[] args) {
		LDAPtest ldap = new LDAPtest();
		ldap.init();
	}

	public void init() {
		DirContext ctx = null;
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://192.168.63.142:389/");//连接LDAP的URL和端口

		env.put(Context.SECURITY_AUTHENTICATION, "simple");//以simple方式发送
		env.put(Context.SECURITY_PRINCIPAL, "wyg@redlotus.com.cn");//用户名
		env.put(Context.SECURITY_CREDENTIALS, "Redlotus01");//密码
		String baseDN = "DC=redlotus,DC=com,DC=cn";//查询区域
		String filter = "(&(objectClass=person))";//条件查询

		try {
			ctx = new InitialDirContext(env);//连接LDAP服务器
			System.out.println("Success");
			SearchControls constraints = new SearchControls();//执行查询操作
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			NamingEnumeration<SearchResult> en = ctx.search(baseDN, filter, constraints);
			if (en == null) {
				System.out.println("There have no value");
			} else {
				while (en.hasMoreElements()) {

					Object obj = en.nextElement();
					if (obj instanceof SearchResult) {
						SearchResult sr = (SearchResult) obj;
						String cn = sr.getName();

						System.out.println("cccccc: " + cn);
					}
				}
			}

		} catch (javax.naming.AuthenticationException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println("erro：" + e);
		}
	}
}