package RoyalEnfield.RoyalEnfield;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import net.minidev.json.JSONObject;


@RestController
@RequestMapping("/Bikes")
public class StoreInfo {
	
	@Autowired
	RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate() {
	RestTemplate rtemp=new RestTemplate();
	return rtemp;
	}
	
	List<Bike> bikeList=new ArrayList<>();
	
	public StoreInfo() {
		bikeList.add(new Bike("SportBike","Interceptor"));
		bikeList.add(new Bike("SportBike","ContinentalGT"));
		bikeList.add(new Bike("MountainBike","Himalayan"));
		bikeList.add(new Bike("MountainBike","CafeRacer"));
		bikeList.add(new Bike("Cruiser","ThunderBird"));
		bikeList.add(new Bike("Cruiser","Meteor"));
		bikeList.add(new Bike("Royal","Classic"));	
	}
	
	@RequestMapping(value="/bikeList")
	public List<Bike> getBikeList(HttpServletRequest  request, HttpServletResponse response)
	{
		return bikeList;
	}
	
	@RequestMapping(value="/info")
	public String getStoreInfo(HttpServletRequest  request, HttpServletResponse response)
	{
		StringBuilder r=new StringBuilder();
		for(Bike b:bikeList)
		{
			r.append("<tr><td>"+b.getBikeType()+"</td> : <td>"+b.getBikeName()+"</td></tr>");
		}
		String res="<html><body><B>Instance Name : " + request.getLocalName() + "<BR>";
		res += "<B>Port : </B>" +  + request.getLocalPort() + "<BR>";
		res += "<table border=\"2\"><thead><th>Bike Type</th><th>Bike Name</th></thead>";
		res+=  r;
		res += "</table></body></html>";
		System.out.println("sever running" + request.getLocalPort());
		return res;
	}
	
	@RequestMapping(value="/updatePage")
	public String updatePage(HttpServletRequest  request, HttpServletResponse response)
	{
		String res="<html><body><B>Instance Name : " + request.getLocalName() + "<BR>";
		res += "<B>Port : </B>" +  + request.getLocalPort() + "<BR>";
		res += "<form method=\"POST\" action=\"/Bikes/updateBike\">";
		res+=  "Bike Type:<input type=\"text\" name=\"bikeType\">";
		res+=  "Bike Name:<input type=\"text\" name=\"bikeName\">";
		res+=  "Old Name:<input type=\"text\" name=\"oldName\">";
		res+=  "<input type=\"submit\" name=\"submit\">";
		res += "</form></body></html>";
		System.out.println("sever running" + request.getLocalPort());
		return res;
	}
	
	@PostMapping("/updateBike")
	public String updateBike(Bike b,String oldName)
	{
		int c=0;
		for(Bike bike:bikeList)
		{
			if(bike.getBikeName().equalsIgnoreCase(oldName))
			{
				bikeList.set(c, b);
			}
			c++;
		}
		return "Succesfully updated "+oldName+" to " +b.toString();
	}
	
	@PostMapping(value="/add",consumes=MediaType.APPLICATION_JSON_VALUE)
	public String addBike(@RequestBody Bike b,HttpServletRequest request, HttpServletResponse response) 
	{
		String message="<HTML><BODY><B>ADDED THE BIKE TO ALL SERVER</B></BODY></HTML>";
		int localPort=request.getLocalPort();
		HashMap<String, String> hmap=new HashMap<>();
		hmap.put("bikeName",b.getBikeName());
		hmap.put("bikeType", b.getBikeType());
		System.out.println(hmap);
		String json=JSONObject.toJSONString(hmap);
		System.out.println("json value "+localPort);

		HttpHeaders httpHead=new HttpHeaders();
		httpHead.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> ent=new HttpEntity<>(json,httpHead);
		if(localPort==9002) {
			boolean bool=false;
			for(Bike bk:bikeList)
				if(bk.getBikeName().equalsIgnoreCase(b.getBikeName()))
					bool=true;
				
			if(!bool) 
			{
				bikeList.add(b);
				String res=restTemplate.exchange("http://localhost:9003/Bikes/add",
							HttpMethod.POST,ent, java.lang.String.class).getBody();
				System.out.println("Send data to 9003 "+res);
				System.out.println("res "+ent.getBody());
			}
			else
				return message;
		}
		else if(localPort==9003) {
			boolean bool=false;
			for(Bike bk:bikeList)
				if(bk.getBikeName().equalsIgnoreCase(b.getBikeName()))
					bool=true;
				
			if(!bool) 
			{
				bikeList.add(b);
				String res=restTemplate.exchange("http://localhost:9004/Bikes/add",
						HttpMethod.POST,ent, java.lang.String.class).getBody();
				System.out.println("send data to 9004 "+res);
				System.out.println("res "+ent.getBody());
			}
			else
				return message;
		}
		else if(localPort==9004) {
			boolean bool=false;
			for(Bike bk:bikeList)
				if(bk.getBikeName().equalsIgnoreCase(b.getBikeName()))
					bool=true;
				
			if(!bool) 
			{
				bikeList.add(b);
				String res=restTemplate.exchange("http://localhost:9002/Bikes/add",
						HttpMethod.POST,ent, java.lang.String.class).getBody();
				System.out.println("send data to 9002 "+res);
				System.out.println("res "+ent.getBody());
			}
			else
				return message;
		}
		
		return "<HTML><BODY><B>BIKE ADDED SUCCESSFULLY</B></BODY></HTML>";
	}
	
//	@PostMapping(value="/addBikeToList",consumes=MediaType.APPLICATION_JSON_VALUE)
//	public void addBikeToList(@RequestBody Bike b,HttpServletRequest request, HttpServletResponse response) {
//		
//		bikeList.add(b);
//		
//	}
}
//		List<Bike> resList=restTemplate.getForEntity("http://localhost:8999/Bikes/bikeList",
//		List.class).getBody();
//		if(!resList.contains(b))
//			{
//			String res=restTemplate.exchange("http://localhost:8999/Bikes/addBikeToList",
//					HttpMethod.POST,ent, java.lang.String.class).getBody();
//					System.out.println("Send data to 8999");
//			}	
//		}	
//		System.out.println("Data insertion done at all server");
//	}
//	return "<HTML><BODY><B>ADDED THE BIKE TO ALL SERVER</B></BODY></HTML>";
//		}