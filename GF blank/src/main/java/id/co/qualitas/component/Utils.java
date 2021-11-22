package id.co.qualitas.component;

import java.awt.image.BufferedImage;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.SimpleDoc;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import id.co.qualitas.config.WebServiceClient;
import id.co.qualitas.domain.Menu;

public class Utils {

	protected static final String ERROR_CODE = null;

	public static Object WebserviceResponse(Object request, String contextPath, String url, String username,
			String password) throws Exception {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath(contextPath);
		WebServiceClient client = new WebServiceClient();
		HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
		messageSender.setCredentials(new UsernamePasswordCredentials(username, password));
		messageSender.afterPropertiesSet();
		messageSender.setConnectionTimeout(1800000);
		messageSender.setReadTimeout(1800000);
		client.setDefaultUri(url);
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		client.setMessageSender(messageSender);
		return client.responseWebService(request, url);
	}
	
	public static Object WebserviceResponse(Object request, String contextPath, String urlSuffix) throws Exception {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath(contextPath);
		WebServiceClient client = new WebServiceClient();
		HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
		messageSender.setCredentials(new UsernamePasswordCredentials(Cons.USERNAME, Cons.PASSWORD));
		messageSender.afterPropertiesSet();
		messageSender.setConnectionTimeout(1800000);
		messageSender.setReadTimeout(1800000);
		client.setDefaultUri(Cons.WEBSERVICE_SAP + urlSuffix);
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		client.setMessageSender(messageSender);
		return client.responseWebService(request, Cons.WEBSERVICE_SAP + urlSuffix);
	}

	public static Map splitUsernameAndClient(String username) {
		Map map = new HashMap();
//		map.put(Constants.USERNAME, tmpSplit[0]);
//		map.put(Constants.CLIENT, tmpSplit[1]);
		map.put(Constants.USERNAME, username);
		map.put(Constants.CLIENT, "");
		return map;
	}

	public static String hashPassword(String password) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

	public static String mergeUsernameAndClient(String username, String client) {
		if (checkPatternUsername(username))
			return username;
		else
			return new StringBuffer().append(username).append(Constants.PIPE).append(client).toString();
	}

	public static boolean checkPatternUsername(String username) {
		return Pattern.matches(Constants.USERNAME_PATTERN, username);
	}
	
	public static Map<?,?> setupMenu(List<Menu> menus,String name) {
		ArrayList<Menu> listMenu= new ArrayList<Menu>();
		ArrayList<Menu> listMenuChild= new ArrayList<Menu>();
		HashMap<Object, Object> mapMenu = new HashMap<Object, Object>();
		boolean flag=false;
		Menu menu = null;
		for (int i = 0; i < menus.size(); i++) {
			menu = (Menu) menus.get(i);
			mapMenu = new HashMap<Object, Object>();
			if(menu.getParentId()==0){
				listMenu.add(menu);
				menus.remove(i);
				i--;
			}
		}
		for (int i = 0; i < listMenu.size(); i++) {
			flag=false;
			listMenuChild= new ArrayList<Menu>();
			for(int j=0;j<menus.size();j++){
				if(listMenu.get(i).getId()==menus.get(j).getParentId()){
					listMenuChild.add(menus.get(j));
					menus.remove(j);
					j--;
					flag=true;
				}
			}
			if(flag){
				listMenu.get(i).setMenu(listMenuChild);
			}
		}
		mapMenu.put("menus", listMenu);
		mapMenu.put("name", name);
		return mapMenu;
	}

//	public static List setupMenu(List menus) {
//		List listMenu = new ArrayList();
//		List listMenuChild = null;
//		Map mapMenu = null;
//		Map mapMenuChild = null;
//		Menu menu = null;
//		Menu menuChild = null;
//		for (int i = 0; i < menus.size(); i++) {
//			menu = (Menu) menus.get(i);
//			if (menu.getMenuType().equals("00")) {
//				mapMenu = new HashMap();
//				listMenuChild = new ArrayList();
//				for (int j = 0; j < menus.size(); j++) {
//					menuChild = (Menu) menus.get(j);
//					if (!menuChild.getMenuType().equals("00") && menuChild.getParentId() == menu.getId()) {
//						mapMenuChild = new HashMap();
//						mapMenuChild.put("id", menuChild.getId());
//						mapMenuChild.put("menu_name", menuChild.getMenuName());
//						mapMenuChild.put("menu_type", menuChild.getMenuType());
//						mapMenuChild.put("controller", menuChild.getController());
//						mapMenuChild.put("path", menuChild.getPath());
//						mapMenuChild.put("template_url", menuChild.getTemplateUrl());
//						mapMenuChild.put("path_hashtag", menuChild.getPathHastag());
//						mapMenuChild.put("priority", menuChild.getPriority());
//						listMenuChild.add(mapMenuChild);
//					}
//				}
//				mapMenu.put("id", menu.getId());
//				mapMenu.put("menu_name", menu.getMenuName());
//				mapMenu.put("menu_type", menu.getMenuType());
//				mapMenu.put("controller", menu.getController());
//				mapMenu.put("path", menu.getPath());
//				mapMenu.put("template_url", menu.getTemplateUrl());
//				mapMenu.put("path_hashtag", menu.getPathHastag());
//				mapMenu.put("priority", menu.getPriority());
//				mapMenu.put("menus", listMenuChild);
//				listMenu.add(mapMenu);
//			}
//		}
//		return listMenu;
//	}

	public static String trimLeadingZeroes(String input) {
		return input != null && !input.isEmpty() ? input.replaceFirst("^0+(?!$)", "") : null;
	}

	public static String trimLeadingZeroes(String input, boolean twoDigit) {
		String output;
		String temp;

		if (input != null && !input.isEmpty()) {

			try {
				temp = input.replaceFirst("^0+(?!$)", "");

				if (twoDigit) {
					output = String.format("%02d", Integer.parseInt(temp));
				} else {
					output = temp;
				}
			} catch (Exception e) {
				output = input;
				System.out.println(e.getMessage());
			}

		} else {
			output = input;
		}

		return output;
	}

	public static String addLeadingZeroes(String input, int digit) {
		String output;
		String temp;

		if (input != null && !input.isEmpty()) {

			try {
				output = String.format("%0"+ digit +"d", BigInteger.valueOf(Long.parseLong(input)));

			} catch (Exception e) {
				output = input;
				System.out.println(e.getMessage());
			}

		} else {
			output = input;
		}

		return output;
	}

	public static String getCurrentDate(String dateFormat) {

		Calendar c = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

		return formatter.format(c.getTime());
	}
	
	public static String validateToString(Object o) {
		return o != null ? o.toString():null; 
	}
}
