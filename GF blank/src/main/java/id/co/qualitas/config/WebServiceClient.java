package id.co.qualitas.config;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class WebServiceClient extends WebServiceGatewaySupport {
	public Object responseWebService(Object object , String url){
		return getWebServiceTemplate().marshalSendAndReceive(object , new SoapActionCallback(url));
	}
}
