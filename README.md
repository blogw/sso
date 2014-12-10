SSO(Single Sign On)
===

### Active Directory install & config
1.[Win2012-AD-Install.xlsx](https://github.com/blogw/sso/blob/master/AD/Win2012-AD-Install.xlsx?raw=true)<br/>
2.[C# AD(Active Directory)域信息同步，组织单位、用户等信息查询](http://www.cnblogs.com/zhongweiv/archive/2012/12/15/ad_sync.html)<br/>
3.加入域的时候，虚拟机不能用NAT，需要用桥接方式

### Apache Directory Studio
1.eclipse-indigo install ok,after indigo not worked<br/>
2.[Troubleshooting LDAP Connections to Active Directory Using Apache Directory Studio](https://jamfnation.jamfsoftware.com/article.html?id=224)<br/>
3.[Using Shiro with an LDAP Server](http://isis.apache.org/components/security/shiro/using-ldap.html)<br/>

### NTLM
[https://code.google.com/p/ntlm-java/](https://code.google.com/p/ntlm-java/) last Update 2011<br/>
Microsoft NT LAN Manager (NTLM) Authentication Protocol implementation in java.<br/>

[http://waffle.codeplex.com/](http://waffle.codeplex.com/) jna<br/>
WAFFLE: Windows Authentication Framework (now only windows OS is supported)<br/>

[http://jcifs.samba.org/](http://jcifs.samba.org/) not support NTLMv2<br/>
JCIFS is an Open Source client library that implements the CIFS/SMB networking protocol in 100% Java<br/>

[http://www.ioplex.com/jespa.html](http://www.ioplex.com/jespa.html) not free<br/>
Jespa - Java Active Directory Integration<br/>

[http://davenport.sourceforge.net/ntlm.html](http://davenport.sourceforge.net/ntlm.html) Last Update 2013<br/>
The NTLM Authentication Protocol and Security Support Provider<br/>

### NTLM1
test JCIFS，only support NTLMv1

        REGEDIT4
        [HKEY_LOCAL_MACHINE\System\CurrentControlSet\Control\Lsa]
        "LMCompatibilityLevel"=dword:00000001

### NTLM2
test Jespa，support NTLMv2

        first run SetupWizard.vbs(use domain administrator account) to create WEB-INF/jespa.properties
### Todo
- [ ] xxxxx
    - [x] aaa
    - [ ] bbb
    - [ ] ccc
- [x] xxxxx
    - [x] dddd
    - [x] eeee
