/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uky.javaworkshop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author pouya
 * +++++++++++++++++++++++++++++Paternal or Maternal, for ECAB3.0+++++++++++++++++++++++++++++++++++
 */
public class ProcessGZFileEcab3 {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //String filename = args[0];
        String filename = "6mo_L57_N100_Trio_ECAB3.vcf.gz";
        try{
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            GZIPInputStream gis = new GZIPInputStream(fis);
            InputStreamReader isr = new InputStreamReader(gis);
            BufferedReader br = new BufferedReader(isr);
            //BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new(FileInputStream(new File(filename))))));
            String line = null;
            String token = null;
            StringTokenizer st = null;
            int index = 0;
            String refAllele = null;
            String varAllele = null;
            String[] genotype = new String[4];
            String chr = null;
            String position = null;
            String interestingFlag = null;
            double qual = -1.0;
            
            while( (line=br.readLine()) != null ) {
                
                if( line.startsWith("##") ){
                    continue;
                }
                
                
                st = new StringTokenizer(line); // tokenize on whitespace character by default
                chr = st.nextToken(); // chr
                position = st.nextToken(); // position
                
                //System.out.print(chr + "\t" + position + "\t");
                st.nextToken();
                
                refAllele = st.nextToken();
                varAllele = st.nextToken();                
                if(!line.startsWith("#")){
                    qual      = Double.parseDouble(st.nextToken()); 
                }else{
                    st.nextToken();
                }            
                for(int i=0; i<3; i++) {
                    st.nextToken();
                }
                
                if( line.startsWith("#") ){
                    while ( st.hasMoreTokens() ) {
                        System.out.print( st.nextToken() + "\t");
                    }
                    System.out.println();
                    System.out.flush();
                    continue;
                }
                index = 0;                
                while( st.hasMoreTokens() ) {
                    genotype[index] = getGenotype(refAllele,varAllele,st.nextToken());  
                    index++;
                }
                
                if(!ignore(genotype)){
                    System.out.print(chr + "\t" + position + "\t" + refAllele + "\t" + varAllele + "\t" + qual + "\t");
                    
                    interestingFlag = isInteresting(genotype);
                    
                    if(interestingFlag!=null){
                        for(int i=0;i<genotype.length;i++){
                            System.out.print(genotype[i] + "("+ interestingFlag +")\t" );
                        }
                        
                    }else{

                        for(int i=0;i<genotype.length;i++){
                            System.out.print(genotype[i] + "\t" );
                        }

                    }
                    
                    
                    
                    System.out.println();
                }
                
                
                System.out.flush();
            }
            
            br.close();
            
        }catch(IOException ioException){
            ioException.printStackTrace();
        }

        // this is as far as we've gotten so far
        // java -cp dist/JavaWorkshop.jar edu.uky.javaworkshop.ProcessGZIPFile genotypes.vcf.gz
    }

    private static String getGenotype(String refAllele, String varAllele, String genotypeIn) {
        StringTokenizer st = new StringTokenizer(genotypeIn,":");
        String genotype = st.nextToken(); // 0/1
/*        if (genotype.equals("0/0")){
            return refAllele+"/"+refAllele;
        } else if (genotype.equals("0/1")){
            return refAllele+"/"+varAllele;
        } else if (genotype.equals("1/1")){
            return varAllele+"/"+varAllele;
        } else if (genotype.equals("./.")){ 
            return genotype;
        } else {
            return "error";
        }
*/      
        StringTokenizer st2 = new StringTokenizer(genotype,"/");
        String allele1 = st2.nextToken(); // 0
        String allele2 = st2.nextToken(); // 1

        switch (allele1) {
            case "0": //do something
                allele1 = refAllele;
                break;
            case "1": //do something
                allele1 = varAllele;
                break;
            case ".": //do nothing
                break;
            default:
                allele1 = "error";
                break;
        }
        switch (allele2) {
            case "0": //do something
                allele2 = refAllele;
                break;
            case "1": //do something
                allele2 = varAllele;
                break;
            case ".": //do nothing
                break;
            default:
                allele2 = "error";
                break;
        }
/*        if (allele1.equals("0")) {
            allele1 = refAllele;
        } else if (allele1.equals("1")){
            allele1 = varAllele;
        } else if (allele1.equals(".")){
            // do nothing
        } else {
            allele1 = "error";
        }
        if (allele2.equals("0")) {
            allele2 = refAllele;
        } else if (allele2.equals("1")){
            allele2 = varAllele;
        } else if (allele2.equals(".")){
            // do nothing
        } else {
            allele2 = "error";
        }
*/
        genotype = allele1 + allele2;
        return genotype;
    }
    
    private static boolean ignore(String[] genotype){
        

        
        if(genotype[2].equals(genotype[3])){
            return true;
        }
        if(genotype[2].equals("..")){
            return true;
        }
        
        if(genotype[3].equals("..")){
            return true;
        }
        
        
        if(genotype[0].equals("..") && genotype[1].equals("..")){
            return true;
        }
            
        return false;
    }
    
    private static String isInteresting(String[] genotype){

        
        if(!genotype[0].equals("..") && isInheritedFrom(genotype[0],genotype[2]) && !isInheritedFrom(genotype[0],genotype[3])){
            return "F1R2_Mat";
        }
        
        if(!genotype[0].equals("..") && !isInheritedFrom(genotype[0],genotype[2]) && isInheritedFrom(genotype[0],genotype[3])){
            return "F1R2_Pat";
        }
        
        if(!genotype[1].equals("..") && isInheritedFrom(genotype[1],genotype[2]) && !isInheritedFrom(genotype[1],genotype[3])){
            return "F2R1_Mat";
        }
        
        if(!genotype[1].equals("..") && !isInheritedFrom(genotype[1],genotype[2]) && isInheritedFrom(genotype[1],genotype[3])){
            return "F2R1_Pat";
        }       
            
        return null;
    }
    
    private static boolean isInheritedFrom(String gt1, String gt2){
        
        char[] alleles1 = gt1.toCharArray();
        char[] alleles2 = gt2.toCharArray();
        
        for(int i=0;i<alleles1.length;i++){
            for(int j=0;j<alleles2.length;j++){
                
                if(alleles1[i]==alleles2[j]){
                    return true;
                }
                
            }
        }
        
        return false;
    }
    

}