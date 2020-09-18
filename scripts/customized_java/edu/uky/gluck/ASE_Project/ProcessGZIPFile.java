/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uky.gluck.ASE_Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author tedkalbfleisch
 */
public class ProcessGZIPFile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String filename = args[0];
        
        try{
            File file = new File(filename);
            FileInputStream fileInputStream = new FileInputStream(file);
            GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream);
            InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            
            String line = null;
            
            //while(true){loop}
            
            StringTokenizer st = null;
            String token = null;
            int index = 0;
            
            String refAllele = null;
            String varAllele = null;
            String genotype = null;
            
            while((line=bufferedReader.readLine())!=null){
                
                if(line.startsWith("#")){
                    continue;
                }
                
                st = new StringTokenizer(line);
                
                st.nextToken();
                st.nextToken();
                st.nextToken();
                refAllele = st.nextToken();
                varAllele = st.nextToken();
                for(int i=0;i<4;i++){
                    st.nextToken();
                }
                
                while(st.hasMoreTokens()){
                    genotype = st.nextToken();
                    System.out.print(getGenotype(refAllele,varAllele,genotype) + "\t");
                }
                System.out.println();
                System.out.flush();
                /*
                while(st.hasMoreTokens()){
                    token = st.nextToken();
                    System.out.println("" + index + "\t" + token);
                    System.out.flush();
                    index++;//index = index + 1;
                }
                break;
                */
            }
            
            bufferedReader.close();
            
        }catch(IOException ioException){
            ioException.printStackTrace();
            
        }
    }
    
    private static String getGenotype(String refAllele,String varAllele,String genotypeIn){
        
        StringTokenizer st = new StringTokenizer(genotypeIn,":");
        String genotype = st.nextToken();
        
        if(genotype.equals("./.")){
            return genotype;
        }
        
        return genotype;
    }
    
}
