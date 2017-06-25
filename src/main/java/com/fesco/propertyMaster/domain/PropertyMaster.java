package com.fesco.propertyMaster.domain;

import com.fesco.propertyMaster.bo.IPropertyMasterBO;
import com.fesco.propertyMaster.model.Environment;
import com.fesco.propertyMaster.model.PropertyUnit;
import com.fesco.propertyMaster.spring.CustomerContextHolder;
import com.fesco.propertyMaster.utils.CommandLineUtils;
import com.fesco.propertyMaster.utils.ConsoleUtils;
import com.fesco.propertyMaster.utils.FileUtils;
import com.fesco.propertyMaster.utils.HttpClientUtils;
import com.fesco.propertyMaster.utils.PathUtils;
import com.fesco.propertyMaster.utils.PropertyUtils;
import com.fesco.propertyMaster.utils.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 配置文件管理核心类
 * @date 10:56 2017/6/13
 * @author 王新宇
 */
public class PropertyMaster {
	static {
		System.setProperty("rootJarPath", PathUtils.getJarPath());
		File file = new File(PathUtils.getJarPath()+System.getProperty("file.separator")+"logs");
		if (!file.exists()) {
			if (!file.mkdir()) {
				throw new RuntimeException("建立日志文件夹失败，应该是没有权限导致");
			}
		}
	}
	private static Logger logger = Logger.getLogger(PropertyMaster.class);
	public static void main(String[] args) {
		try {
			ConsoleUtils.println("您好，欢迎使用本软件！");
			ConsoleUtils.println("  8888888888888888888888888888888888888888  ");
			ConsoleUtils.println(" 88                                      88 ");
			ConsoleUtils.println("88    8888                                88");
			ConsoleUtils.println("88   88888                                88");
			ConsoleUtils.println("88   88  88                               88");
			ConsoleUtils.println("88  88   88                               88");
			ConsoleUtils.println("88  88888888                              88");
			ConsoleUtils.println("88 888   888         888                  88");
			ConsoleUtils.println("88 88      88       88888                 88");
			ConsoleUtils.println("88                 8888888                88");
			ConsoleUtils.println("88                888888888               88");
			ConsoleUtils.println("88               88888888888              88");
			ConsoleUtils.println("88              8888888888888             88");
			ConsoleUtils.println("88            88888888888888888           88");
			ConsoleUtils.println("88            88888888888888888           88");
			ConsoleUtils.println("88              8888888888888             88");
			ConsoleUtils.println("88                  8888                  88");
			ConsoleUtils.println("88                 888888                 88");
			ConsoleUtils.println("88                                        88");
			ConsoleUtils.println("88                          88      88    88");
			ConsoleUtils.println("88                           88    888    88");
			ConsoleUtils.println("88                           88888888     88");
			ConsoleUtils.println("88                            88  888     88");
			ConsoleUtils.println("88                             88 88      88");
			ConsoleUtils.println("88                              88        88");
			ConsoleUtils.println(" 88                                      88 ");
			ConsoleUtils.println("  8888888888888888888888888888888888888888  ");
			//先将用户配置文件模板复制到jar包同级目录中的config文件夹中的config.properties

		/*FileUtils.copyFile(PathUtils.getClassPathFilePath("configTemplate.properties"),
				PathUtils.getJarPath()+System.getProperty("file.separator")+"config"+System.getProperty("file.separator")+"config.properties"
				,false);*/
			if (!FileUtils.copyFile(Thread.currentThread().getContextClassLoader().getResourceAsStream("configTemplate.properties"),
					PathUtils.getJarPath()+System.getProperty("file.separator")+"config"+System.getProperty("file.separator")+"config.properties"
					,false)) {
				logger.error("复制配置文件错误");
				throw new RuntimeException("复制配置文件错误");
			}
			//下面判断配置文件中关键值是否被填充，即：判断配置文件是否满足可以运行的标准
			if (StringUtils.nullOrEmptyString(PropertyUtils.getValue("svnUserName"))
					||StringUtils.nullOrEmptyString(PropertyUtils.getValue("svnPassword"))) {
				ConsoleUtils.println("这是您第一次使用本软件，SVN用户名，密码和一些基础配置需要您配置到");
				ConsoleUtils.println("config/config.properties中，更多配置问题可以参考ReadMe.txt文件");
				return;
			}
			//创建临时文件夹temp，每次程序启动都会清空里面的文件
			File tempFileDir = new File(PathUtils.getJarPath()+System.getProperty("file.separator")+"temp");
			FileUtils.deleteAllFilesOfDir(tempFileDir);
			tempFileDir = new File(PathUtils.getJarPath()+System.getProperty("file.separator")+"temp");
			if (!tempFileDir.mkdir()) {
				logger.error("建立temp文件夹失败");
				throw new RuntimeException("建立temp文件夹失败");
			}
			//初始化一个treeMap
			Map<Environment,PropertyUnit> propertyUnitMap = new TreeMap<Environment, PropertyUnit>(new Comparator<Environment>() {
				public int compare(Environment o1, Environment o2) {
					if(o1.getSortLevel()>o2.getSortLevel()){
						return 1;
					}else if(o1.getSortLevel()<o2.getSortLevel()){
						return -1;
					}else{
						return 0;
					}
				}
			});
			for(Environment environment : Environment.values()){
				PropertyUnit propertyUnit = new PropertyUnit();
				propertyUnit.setDbUrl(PropertyUtils.getValue("dataBase."+ environment +".url"));
				propertyUnit.setDbUserName(PropertyUtils.getValue("dataBase."+ environment +".userName"));
				propertyUnit.setDbPassword(PropertyUtils.getValue("dataBase."+ environment +".pswd"));
				propertyUnit.setSvnUrl(PropertyUtils.getValue("svnPropertyFile."+ environment));
				propertyUnit.setSvnUserName(PropertyUtils.getValue("svnUserName"));
				propertyUnit.setSvnPassword(PropertyUtils.getValue("svnPassword"));
				if(propertyUnit.isIntegrated()){
					ConsoleUtils.println("开始从svn导出"+ environment +"环境的配置文件");
					CommandLineUtils.executeCommond("svn export "+propertyUnit.getSvnUrl()+" "+
							PathUtils.getJarPath()+System.getProperty("file.separator")+"temp"+System.getProperty("file.separator")+
							"appconfig-"+ environment +".properties"+" --username "+propertyUnit.getSvnUserName()+" --password "+
							propertyUnit.getSvnPassword());
					ConsoleUtils.println("导出成功");
					propertyUnit.setPropertyFile(new File(PathUtils.getJarPath()+System.getProperty("file.separator")+"temp"+System.getProperty("file.separator")+
							"appconfig-"+ environment +".properties"));
					//propertyUnit.setPropertyFile(new File(URLDecoder.decode(Thread.currentThread().getContextClassLoader().getResource("appconfig-dev.properties").getPath(),"utf-8")));
					//System.out.println(URLDecoder.decode(Thread.currentThread().getContextClassLoader().getResource("appconfig-dev.properties").getPath(),"utf-8"));
					propertyUnitMap.put(environment,propertyUnit);
				}
			}
			ConsoleUtils.println("经过对配置文件的校验，下面环境的配置文件将会被执行到数据库中");
			Iterator<Map.Entry<Environment, PropertyUnit>> entryIterator = propertyUnitMap.entrySet().iterator();
			while (entryIterator.hasNext()){
				ConsoleUtils.print(entryIterator.next().getKey().toString()+"  ");
			}
			//从用户配置的配置文件中读取数据，初始化system properties，为spring启动做准备
			outFor:
			for(Environment environment : Environment.values()){
				entryIterator = propertyUnitMap.entrySet().iterator();
				while (entryIterator.hasNext()) {
					Map.Entry<Environment, PropertyUnit> next = entryIterator.next();
					if (environment.toString().equals(next.getKey().toString())) {
						System.setProperty("db"+ StringUtils.getFirstWordCapital(next.getKey().toString())+"Url", next.getValue().getDbUrl());
						System.setProperty("db"+StringUtils.getFirstWordCapital(next.getKey().toString())+"UserName", next.getValue().getDbUserName());
						System.setProperty("db"+StringUtils.getFirstWordCapital(next.getKey().toString())+"Password", next.getValue().getDbPassword());
						continue outFor;
					}
				}
				System.setProperty("db"+ StringUtils.getFirstWordCapital(environment.toString())+"Url", "");
				System.setProperty("db"+StringUtils.getFirstWordCapital(environment.toString())+"UserName", "");
				System.setProperty("db"+StringUtils.getFirstWordCapital(environment.toString())+"Password", "");
			}

			//启动spring
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
			IPropertyMasterBO propertyMasterBO = (IPropertyMasterBO) applicationContext.getBean("propertyMasterBO");
			if (propertyMasterBO==null){
				throw new Exception("获得BO层实例为空");
			}
			//循环调用bo
			Iterator<Map.Entry<Environment, PropertyUnit>> iterator = propertyUnitMap.entrySet().iterator();
			while (iterator.hasNext()){
				Map.Entry<Environment, PropertyUnit> entry = iterator.next();
				//切换数据源
				CustomerContextHolder.setDateSourceType(entry.getKey());
				ConsoleUtils.println("切换数据源到"+entry.getKey()+"环境");

				propertyMasterBO.insertNewConfig(propertyMasterBO.getInsertConfigs(entry.getValue()));
				propertyMasterBO.deleteConfig(propertyMasterBO.getDeleteConfigs(entry.getValue()));
				propertyMasterBO.updateConfig(propertyMasterBO.getUpdateConfigs(entry.getValue()));
				ConsoleUtils.println("开始更新"+entry.getKey()+"环境应用内部配置缓存");
				httpInvokeMis(entry.getKey());
			}
			ConsoleUtils.println("执行完毕，欢迎再次使用本软件！");
		}catch (Exception e){
			logger.error("运行出错",e);
			throw new RuntimeException("运行出错",e);
		}
	}

	private static void httpInvokeMis(Environment environment) {
		try {
			String loginUrl = PropertyUtils.getValue("app.login.URL." + environment);
			String refreshConfigCacheUrl = PropertyUtils.getValue("app.refreshConfigCache.URL." + environment);
			if(!StringUtils.nullOrEmptyString(loginUrl)&&!StringUtils.nullOrEmptyString(refreshConfigCacheUrl)){
				String url = loginUrl;
				HttpPost httpPost = new HttpPost(url);
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				nvps.add(new BasicNameValuePair("user.loginName", "fesco"));
				nvps.add(new BasicNameValuePair("user.password", "78e4a3c34ae6feac093ae13cb75a25f3"));
				nvps.add(new BasicNameValuePair("passError", "0"));
				httpPost.setEntity(new UrlEncodedFormEntity(nvps));
				ConsoleUtils.println("调用"+environment+"环境mis应用Http地址："+url+"，模拟登陆");
				HttpResponse response = HttpClients.createDefault().execute(httpPost);
				ConsoleUtils.println("调用"+environment+"环境，模拟登陆成功");
				String set_cookie = response.getFirstHeader("Set-Cookie").getValue();
				if(!StringUtils.nullOrEmptyString(PropertyUtils.getValue("app.selectRole.URL." + environment))
						&& !StringUtils.nullOrEmptyString(PropertyUtils.getValue("app.roleId." + environment))){
					url=PropertyUtils.getValue("app.selectRole.URL." + environment);
					httpPost = new HttpPost(url);
					nvps = new ArrayList<NameValuePair>();
					nvps.add(new BasicNameValuePair("userRole.id", PropertyUtils.getValue("app.roleId." + environment)));
					nvps.add(new BasicNameValuePair("userRole.isEntrusted", "0"));
					httpPost.setEntity(new UrlEncodedFormEntity(nvps));
					httpPost.setHeader("Cookie",set_cookie);
					ConsoleUtils.println("调用"+environment+"环境mis应用Http地址："+url+"，选择角色，本次选择角色ID是:"+ PropertyUtils.getValue("app.roleId." + environment));
					response = HttpClients.createDefault().execute(httpPost);
					String resolved = HttpClientUtils.resolveResponse(response);
					if (resolved.contains(new String("欢迎登录业务运营系统".getBytes("utf-8"),"utf-8"))) {
						ConsoleUtils.println("选择角色成功");
					}else {
						ConsoleUtils.println("选择角色失败，尝试不选角色直接进行下一步");
					}
				}
				url = refreshConfigCacheUrl;
				httpPost = new HttpPost(url);
				httpPost.setHeader("Cookie",set_cookie);
				httpPost.setHeader("Connection","keep-alive");
				httpPost.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
				ConsoleUtils.println("调用"+environment+"环境mis应用Http地址："+url+"，更新应用配置缓存");
				response = HttpClients.createDefault().execute(httpPost);
				HttpEntity entity = response.getEntity();
				StringBuilder result = new StringBuilder();
				if (entity != null) {
					InputStream instream = entity.getContent();
					BufferedReader br = new BufferedReader(new InputStreamReader(instream));
					String temp = "";
					while ((temp = br.readLine()) != null) {
						String str = new String(temp.getBytes(), "utf-8");
						result.append(str);
					}
				}
				if(result.toString().replaceAll("\"","").equals("success")){
					ConsoleUtils.println("调用"+environment+"环境mis应用修改全局配置成功");
				}else{
					ConsoleUtils.println("调用"+environment+"环境mis应用修改全局配置失败");
				}
			}else{
				return;
			}
		} catch (UnsupportedEncodingException e) {
			ConsoleUtils.println("HTTP远程调用失败，可能是项目没有启动，但数据库已经更新完毕");
		} catch (ClientProtocolException e) {
			ConsoleUtils.println("HTTP远程调用失败，可能是项目没有启动，但数据库已经更新完毕");
		} catch (IOException e) {
			ConsoleUtils.println("HTTP远程调用失败，可能是项目没有启动，但数据库已经更新完毕");
		}
	}
}
