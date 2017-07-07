# propertyMaster
欢迎使用配置文件管理专家

1.将压缩包中的所有文件解压到本地空文件夹中，解压后的目录结构如下：
			--+某个文件夹
				--+propertyMaset.jar
				--+propertyMaster.bat
2.点击运行propertyMaster.bat或者使用java命令执行 java -jar propertyMaset.jar
3.首次运行会在当前文件夹目录下创建几个文件夹，目录结构如下：
			--+某个文件夹
				--+propertyMaset.jar
				--+propertyMaster.bat
				--+config
					--+config.properties		用户需要自行修改这个配置方可正确执行程序
				--+logs		日志文件夹
				--+temp		从svn下载下来的不同环境的配置文件，每次执行程序都会重新下载
4.首次运行并不会执行主体程序，而是要求用户填写配置文件
5.config.properties配置说明：
		1).默认带有开发环境的数据库信息
		2).配置哪些数据库信息，程序就会执行哪些环境的配置文件到对应的数据库
		3).用户需要自行配置SVN用户名和密码，这个用户名会当做操作数据库的操作人记录到数据库中
		4).下面讲解具体配置名称的作用
				a.dataBase:数据库		填写便会执行对应环境，不填写不会执行对应环境，数据库地址可以仿照模板
				b.svnUserName:svn用户名		必填
				c.svnPassword:svn密码		必填
				d.svnPropertyFile:配置文件svn路径，需要具体到对应环境的对应配置文件		对应数据库配置，数据库信息填写，这里必须填写
				f.app.login.URL:登录使用的action		对应数据库配置，数据库信息填写，这里必须填写
				g.app.login.userName:模拟登录使用的用户名		必须填写，如果不填写，需要手动去响应的环境更新
				h.app.login.userPassword:模拟登录用户名对应的密码		必须填写，如果不填写，需要手动去响应的环境更新
				i.app.selectRole.URL:选择角色使用的actoin		如果大F管理员用户有多个角色需要选择，必填
				j.app.roleId:角色ID，如果使用了选择角色action，需要填写具体的角色ID		如果大F管理员用户有多个角色需要选择，必填
				k.app.refreshConfigCache.URL:更新对应环境中配置缓存的action		对应数据库配置，数据库信息填写，这里必须填写
6.正确配置好配置文件后，重复2步骤即可

-------------------------------------------------------------------------------------------------------------------------------------
2017年7月2日更新内容：
配置文件模板中增加了app.login.userName和app.login.userPassword，由于开发人员不知道生产环境大F管理员密码，需要使用者自行填写。

