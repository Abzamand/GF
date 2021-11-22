package id.co.qualitas.component;

public class Cons {
	
	//oldie
//	public static final String USERNAME="QUAL_FUNC01";
//	public static final String PASSWORD="Tudung_2018";
	
//	public static final String USERNAME="interface";//prod
	public static final String USERNAME="QUAL_FUNC01";//dev
	public static final String PASSWORD="garudafood"; 
//	public static final String PASSWORD="garudafood1"; 
//	test commit
	//dev
	public static final String WEBSERVICE_SAP= "http://SAPEDE01:8010/sap/bc/srt/rfc/sap/";
	//prod
//	public static final String WEBSERVICE_SAP= "http://SAPEPRCI.garudafood.co.id:8030/sap/bc/srt/rfc/sap/";
	
	//dev
	public static final String _SAP_CLIENT_ = "110";
	//prod
//	public static final String _SAP_CLIENT_ = "300";


	public static final String ENDPOINT_SLOC = "zwb_sloc/"+ _SAP_CLIENT_ +"/zwb_sloc/zwb_sloc";
	public static final String ENDPOINT_PLANT = "zwb_plant/"+ _SAP_CLIENT_ +"/zwb_plant/zwb_plant";
	public static final String ENDPOINT_MATERIAL = "zwb_matnr/"+ _SAP_CLIENT_ +"/zwb_matnr/zwb_matnr";
	public static final String ENDPOINT_MATERIAL_TYPE = "zwb_mtart/"+ _SAP_CLIENT_ +"/zwb_mtart/zwb_mtart";
	public static final String ENDPOINT_VENDOR = "zwb_vendor/"+ _SAP_CLIENT_ +"/zwb_vendor/zwb_vendor";
	public static final String ENDPOINT_STOCK_OEM = "zwb_stock_oem/"+ _SAP_CLIENT_ +"/zwb_stock_oem/zwb_stock_oem";
	
	public static final String ENDPOINT_TPOSTING = "zwb_tp/"+ _SAP_CLIENT_ +"/zwb_tp/zwb_tp";
	public static final String ENDPOINT_POFG = "zwb_pofg/"+ _SAP_CLIENT_ +"/zwb_pofg/zwb_pofg";
	public static final String ENDPOINT_POSTOSO = "zwb_posto_so/"+ _SAP_CLIENT_ +"/zwb_posto_so/zwb_posto_so";
	public static final String ENDPOINT_SHIPM_DO = "zwb_wb_shipm_do/"+ _SAP_CLIENT_ +"/zwb_wb_shipm_do/zwb_wb_shipm_do";
	public static final String ENDPOINT_PO_STO = "zwb_po_sto/"+ _SAP_CLIENT_ +"/zwb_po_sto/zwb_po_sto";
	public static final String ENDPOINT_GOOD_SMVT = "zwb_goodsmvt/"+ _SAP_CLIENT_ +"/zwb_goodsmvt/zwb_goodsmvt";
	public static final String ENDPOINT_TP_SENDER = "zwb_tp_sender/"+ _SAP_CLIENT_ +"/zwb_tp_sender/zwb_tp_sender";
	public static final String ENDPOINT_POD_STATUS = "zwb_pod_status/"+ _SAP_CLIENT_ +"/zwb_pod_status/zwb_pod_status";
	public static final String ENDPOINT_CREATE_DO = "zwb_create_do/"+ _SAP_CLIENT_ +"/zwb_create_do/zwb_create_do";
	public static final String ENDPOINT_SHIPMENT_CHANGE = "zwb_shipment_change/"+ _SAP_CLIENT_ +"/zwb_shipment_change/zwb_shipment_change";
	
	
	public static final String CONTEXT_TPOSTING = "id.co.qualitas.domain.webservice.tposting";
	public static final String CONTEXT_SLOC = "id.co.qualitas.domain.webservice.sloc";
	public static final String CONTEXT_PLANT = "id.co.qualitas.domain.webservice.plant";
	public static final String CONTEXT_MATERIAL = "id.co.qualitas.domain.webservice.material";
	public static final String CONTEXT_MATERIAL_TYPE = "id.co.qualitas.domain.webservice.materialtype";
	public static final String CONTEXT_VENDOR = "id.co.qualitas.domain.webservice.vendor";
	public static final String CONTEXT_POFG = "id.co.qualitas.domain.webservice.pofg";
	public static final String CONTEXT_STOCK_OEM = "id.co.qualitas.domain.webservice.stockoem";
	public static final String CONTEXT_POSTOSO = "id.co.qualitas.domain.webservice.postoso";
	public static final String CONTEXT_SHIPM_DO = "id.co.qualitas.domain.webservice.shipmdo";
	public static final String CONTEXT_PO_STO = "id.co.qualitas.domain.webservice.posto";
	public static final String CONTEXT_GOOD_SMVT = "id.co.qualitas.domain.webservice.goodsmvt";
	public static final String CONTEXT_TP_SENDER = "id.co.qualitas.domain.webservice.tpsender";
	public static final String CONTEXT_POD_STATUS = "id.co.qualitas.domain.webservice.podstatus";
	public static final String CONTEXT_CREATE_DO = "id.co.qualitas.domain.webservice.createdo";
	public static final String CONTEXT_SHIPMENT_CHANGE = "id.co.qualitas.domain.webservice.shipmentchange";


	public static final String DATE_FORMAT_1 = "yyyyMM";
	public static final int MAX_DIGIT_MATERIAL_NO_SAP = 18;
	

	
	// Set required configs
//	public static final String EMAIL_HOST = "smtp.gmail.com";
	public static final String EMAIL_HOST = "zmail.garudafood.co.id";
//		smtp.office365.com
	public static final String EMAIL_PORT = "587";
	public static final boolean EMAIL_START_TLS = true;
//	public static final String BASE_EMAIL = "testerwilxoxo@gmail.com";//TODO ganti jadi email GF
	public static final String BASE_EMAIL = "OEMMOB@garudafood.co.id";
//	public static final String BASE_EMAIL_PASSWORD = "xoxotesterwil";
	public static final String BASE_EMAIL_PASSWORD = "B1ntaro10@";
//	public static final String PATH_TEMP_PREFIX = "C:\\Users\\tjowi\\Documents";
	public static final String PATH_TEMP_PREFIX = "F:\\oem\\Publish\\pdf_temp";
	public static final String PATH_TEMP_SUFFIX = "temp.pdf";
	
	public static final String DUMMY_POSTO_PJDASHDAT = "202012";
	public static final String DUMMY_POSTO_PLGORT = "3031";
	public static final String DUMMY_POSTO_PWERKS = "3014";
	public static final String DUMMY_POSOBU_PJDASHDAT = "202012";
	public static final String DUMMY_POSOBU_PLGORT = "3031";
	public static final String DUMMY_POSOBU_PWERKS = "3014";
	public static final String DOC_TYPE_STO = "STO";
	public static final String DOC_TYPE_SO = "SOBU";
	
	public static final String TP_HEADER_STATUS_ALL = "%%";
	public static final String TP_HEADER_STATUS_ON_PROGRESS = "On Progress";
	
	public static final String TP_CONFIRM_DOC_TYPE = "315";
	public static final String VENDOR_CONFIRM_DOC_TYPE = "541";
	public static final String TP_CONFIRM_DEFAULT_GM_CODE = "04";
	
	public static final String MENU_PROCESS = "P";
	public static final String MENU_OUTGOING = "O";
	
	public static final String STATUS_PGI_COMPLETED = "PGI COMPLETED";
	public static final String STATUS_PGI_FAILED = "PGI FAILED";
	
	public static final String SHIPM_DO_INFIX_NOT_NULL = "O";
	public static final String SHIPM_DO_INFIX_NULL = "X";
}
