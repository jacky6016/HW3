/* Author: 林展翔 Dept: EE(junior) StudentID: E24019067 */

import java.io.*;
import java.net.*;	// for downloading file
import org.json.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TocHw3
{
	public static void main(String[] args)
    {
		/* arg[0]: URL  arg[1]: 鄉鎮市區  arg[2]: 路名  arg[3]: 交易年月 */
		try
		{
			String url = args[0];
			String TOWN = args[1];
			String ROAD = args[2];
			String YEAR = args[3];
			int MONTH = Integer.parseInt(YEAR)*100;
			int total = 0;
			int count = 0;
			int average;
			//JSONArray jsonRealPrice = new JSONArray(new JSONTokener(new FileReader(new File(args[0]))));
			
			TocHw3 hw3 = new TocHw3();
			hw3.parseData(url);
			JSONArray jsonRealPrice = new JSONArray(new JSONTokener(new FileReader(new File("Data.txt"))));
						
			// Get the keys of the corresponding names
			for(int i=0; i<jsonRealPrice.length(); i++)
			{			
				JSONObject object = jsonRealPrice.getJSONObject(i);
				
				String town = object.getString("鄉鎮市區");
				String address = object.getString("土地區段位置或建物區門牌");
				int month = object.getInt("交易年月");
				int price = object.getInt("總價元");
				
				if( town.equals(TOWN) )
				{
					if(month > MONTH)
					{
						if( matchRoad(ROAD,address) )
						{
							total = total + price;
							count++;
							/*
							System.out.println(town);
							System.out.println(address);
							System.out.println(month);
							System.out.println(price);
							System.out.println();
							*/
						}
					}
				}
			}
			average = total/count;
			System.out.println(average);			
		}
		catch(FileNotFoundException ex)
		{
			System.out.println("Data File Not Found");
			ex.printStackTrace();
		}
		catch(JSONException ex)
		{
			System.out.println("JSON Exception");
		}
	}
	
	public void parseData(String url) 
	{
		try
		{
			URL pageUrl = new URL(url);
			// 讀入網頁(位元串流)
			BufferedReader br = new BufferedReader(new InputStreamReader(pageUrl.openStream(), "UTF-8"));
			BufferedWriter bw = new BufferedWriter(new FileWriter("Data.txt", false));
			String oneLine = null ;
		
			while ((oneLine = br.readLine()) != null) 
			{
				bw.write(oneLine);
				bw.flush();
				//System.out.println(oneLine);
			}
			br.close();
			bw.close();
		//System.out.println("parse Done");
		}
		catch (IOException ex) 
		{
			ex.printStackTrace();
		}
	}
	
	public static boolean matchRoad(String road, String address)
	{
		String re = ".*" + road + ".*";
		Pattern pattern = Pattern.compile(re);
		Matcher matcher = pattern.matcher(address);
		if(matcher.find())
			return true;
		else
			return false;
	}
}	
