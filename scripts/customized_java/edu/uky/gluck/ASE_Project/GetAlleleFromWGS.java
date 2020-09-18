package edu.uky.gluck.ASE_Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class GetAlleleFromWGS {

    public static void main(String[] args){

		String wgsAlleleFilename = args[0];
		String rnaAlleleFilename = args[1];
		try {
		
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(wgsAlleleFilename)));
			
			bufferedReader.readLine();
			String line = null;
			HashMap alleleHash = new HashMap();
			String[] tokens = null;
			
			while((line=bufferedReader.readLine())!=null) {
				tokens = line.split("\t");
				alleleHash.put(tokens[0] + ":" + tokens[1], tokens[6]);
				
			}
			
			
			bufferedReader.close();
			
			bufferedReader = new BufferedReader(new FileReader(new File(rnaAlleleFilename)));
			
			System.out.println(bufferedReader.readLine());
			String key = null;
			String val = null;
			
			while((line=bufferedReader.readLine())!=null) {
				
				tokens = line.split("\t");
				key = tokens[12] + ":" + tokens[13];
				
				System.out.print(line);
				if(alleleHash.containsKey(key)) {
					val = (String)alleleHash.get(key);
					System.out.print("\t" + val);
				}
				System.out.println();
				System.out.flush();
			}
			
			bufferedReader.close();
			/*
			Set set = alleleHash.keySet();
			Iterator iterator = set.iterator();

			
			while(iterator.hasNext()) {
				
				key = (String)iterator.next();
				val = (String)alleleHash.get(key);
				
				System.out.println(key + "\t" + val);
				System.out.flush();
				
			}
			*/
		}catch(IOException ioException) {
			ioException.printStackTrace();
		}
		
	}
	
}
