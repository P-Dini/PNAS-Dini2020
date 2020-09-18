/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uky.gluck.ASE_Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 *
 * @author tedkalbfleisch
 */
public class AnnotateVCF {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try{
            
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("EquCab3.0_PD.gff")));
            
            String line = null;
            StringTokenizer st = null;
            ArrayList arrayList = null;
            HashMap chromosomeHash = new HashMap();
            
            String chromosome = null;
            String type = null;
            int startPos = -1;
            int endPos   = -1;
            String geneSymbol = null;
            String geneId = null;
            int position = 0;
            String token = null;
            
            Gene gene  = null;
            
            while((line=bufferedReader.readLine())!=null){
                
                if(line.startsWith("#")){
                    continue;
                }
                
                st = new StringTokenizer(line,"\" \t;,=:");
                
                chromosome = st.nextToken();
                st.nextToken();
                type = st.nextToken();
                
                if(!type.equals("gene")){
                    continue;
                }
                
                startPos = Integer.parseInt(st.nextToken());
                endPos   = Integer.parseInt(st.nextToken());
                geneId = "-";
                geneSymbol = "-";
                while(st.hasMoreTokens()){
                    token = st.nextToken();
                    if(token.equals("GeneID")){
                        geneId = st.nextToken();
                    }if(token.equals("Name")){
                        geneSymbol = st.nextToken();
                    }
                }

                if(chromosomeHash.containsKey(chromosome)){
                    arrayList = (ArrayList)chromosomeHash.get(chromosome);
                }else{
                    arrayList = new ArrayList();
                }
                
                gene = new Gene();
                gene.setChromosome(chromosome);
                gene.setStartPos(startPos);
                gene.setEndPos(endPos);
                gene.setGeneSymbol(geneSymbol);
                gene.setGeneId(geneId);
                
                arrayList.add(gene);
                
                chromosomeHash.put(chromosome, arrayList);
            }
            
            bufferedReader.close();
            
            bufferedReader = new BufferedReader(new FileReader(new File(args[0])));
            
            String lastChr = "";
            
            while((line=bufferedReader.readLine())!=null){
                
                if(line.startsWith("#")){
                    continue;
                }
                
                st = new StringTokenizer(line);
                chromosome = st.nextToken();
                position = Integer.parseInt(st.nextToken());
                
                if(!lastChr.equals(chromosome)){
                    
                    arrayList = (ArrayList)chromosomeHash.get(chromosome);
                    
                }
                
                geneSymbol = getGeneSymbol(arrayList,position);
                
                System.out.println(chromosome + "\t" + position + "\t" + geneSymbol);
                System.out.flush();
                
                lastChr = chromosome;
            }
            
        }catch(IOException ioException){
            ioException.printStackTrace();
        }
        
    }
    
    private static String getGeneSymbol(ArrayList arrayList, int position){
        
        String geneSymbol = "-\t-";
        Gene gene = null;
        
        for(int i=0;i<arrayList.size();i++){
            
            gene = (Gene)arrayList.get(i);
            
            if(position>=gene.getStartPos() && position<=gene.getEndPos()){
                geneSymbol = gene.getGeneSymbol() + "\t" + gene.getGeneId();
                break;
            }
        }
        
        return geneSymbol;
    }
    
}
