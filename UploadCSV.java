package excel;

import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.wellpoint.wdss.dao.hub.CasePodDAO;
import com.wellpoint.wdss.dao.hub.HubDAO;
import com.wellpoint.wdss.dao.hub.HubPodConfigurationDAO;
import com.wellpoint.wdss.dao.hub.HubToPodRelationDAO;
import com.wellpoint.wdss.model.CasePod;
import com.wellpoint.wdss.model.Hub;
import com.wellpoint.wdss.model.HubPodConfiguration;
import com.wellpoint.wdss.model.HubToPodRelation;
import com.wellpoint.wdss.services.hub.HubToPodRelationService;

public class UploadCSV {
	
	
	 private static final String FILE_NAME_CSV = "D:\\SHETTY\\Hub_Excel.csv";
	 
	 private HubDAO hubDAO;
	 
	 private CasePodDAO casePodDAO;
	 
	 private HubToPodRelationDAO hubToPodRelationDAO;
	 
	 private HubPodConfigurationDAO config;
	 
	 private HubToPodRelationService service;
	 
	 
	public   void uploadCSV(String filePath) {
		
		 try{
			 CSVFormat format = CSVFormat.RFC4180.withHeader().withDelimiter(',');
		        CSVParser reader = new CSVParser((new FileReader(new File(FILE_NAME_CSV))),format);
		        List<Hub>  hubList=hubDAO.getAllHubs();
		        List<CasePod> podList=casePodDAO.getAllCasePods();
		        Map<String, Hub> hubMap=new HashMap<String, Hub>();
		        Map<String, CasePod> podMap=new HashMap<String, CasePod>();	
		        for(Hub hub:hubList)
		        {
		        	hubMap.put(hub.getHubName(), hub);
		        	
		        }
		        for(CasePod casePod:podList)
	        	{
	        		podMap.put(casePod.getPodName(), casePod);
	        	}
		        hubList.clear();
		        podList.clear();
		        
		        
		        List<HubPodConfiguration> hubPodConfigurationList=new ArrayList<HubPodConfiguration>();
		            for (CSVRecord record  : reader) {
		                System.out.println(record);
		                HubPodConfiguration hubPodConfiguration=new HubPodConfiguration();
		                hubPodConfiguration.setGroupId(new BigInteger(record.get("groupId")));
		                
		                hubPodConfiguration.setFundingTypeCd(record.get("fundingTypeCd"));
		                hubPodConfiguration.setStateSold(record.get("stateSold"));
		                hubPodConfiguration.setGroupName(record.get("groupName"));
		                

		                HubToPodRelation hubToPodRelation=new HubToPodRelation();
		                hubToPodRelation.setHubDetails(hubMap.get(record.get("hub")));
		                hubToPodRelation.setPodDetails(podMap.get(record.get("pod")));
		                
		                hubPodConfiguration.setHubToPodRelationDetails(hubToPodRelation);
		                
		                config.saveHubPodConfiguration(hubPodConfiguration);
		}
		            
		            
		 }catch
		 (Exception e)
		 {
			 e.printStackTrace();
		 }
	}

}
