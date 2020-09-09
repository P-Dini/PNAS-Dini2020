package edu.uky.gluck.ConservedRegionAnalysis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.biojava.nbio.genome.parsers.gff.FeatureI;
import org.biojava.nbio.genome.parsers.gff.FeatureList;
import org.biojava.nbio.genome.parsers.gff.GFF3Reader;
import org.biojava.nbio.genome.parsers.gff.Location;

import htsjdk.samtools.util.CloseableIterator;
import htsjdk.variant.variantcontext.Genotype;
import htsjdk.variant.variantcontext.VariantContext;
import htsjdk.variant.vcf.VCFFileReader;
import htsjdk.variant.vcf.VCFHeader;
import java.io.BufferedReader;
import java.io.FileReader;



public class ParseVCF_Custom {

    public static void main(String[] args) {

        //pdString TWILIGHT = "Twilight";
        //pdString[] swaybacks = {"3517","3519"};
        //pdString[] otherHorses = {"3527","H_2158","H_3958","ST22","TB03","TB10","Twilight"};

        try {

            FeatureList featureList = null;
            Iterator iterator = null;
            Location location = null;
            FeatureI feature = null;
            String gene = null;
            String gbkey = null;
            /*
            VCFFileReader r=new VCFFileReader(new File("/Users/pouya/Desktop/test.vcf") );
            //VCFFileReader r=new VCFFileReader(new File("/Users/pouya/Dropbox\\ \\(Personal\\)/Bioinformatic/Java/NewFolder/JavaApplication5/4mo_L40_N100_Trio_Placenta.vcf.gz") );

            VCFHeader vcfHeader = r.getFileHeader();
            */
            featureList = GFF3Reader.read("/Users/pouya/Downloads/ref_EquCab3.0_top_level.gff3");
            /*
            ArrayList sampleNames = vcfHeader.getSampleNamesInOrder();

            for(int i=0;i<sampleNames.size();i++) {
                    System.out.println(sampleNames.get(i));
            }

            */
            //CloseableIterator<VariantContext> t=r.iterator();

            //pdCloseableIterator<VariantContext> t=r.query("chr20", 41931756, 45986467);
            //pdGenotype genotype = null;
            //VariantContext ctx = null;
            //pdboolean breakFlag = false;
            //comment on this and add the thing 
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(args[0])));
            String line = null;
            String[] tokens = null;
            String chromosome = null;
            int position = -1;

            while((line=bufferedReader.readLine())!=null){

                if(line.startsWith("#"))
                    continue;

                tokens = line.split("\t");

                chromosome = tokens[0];
                position = Integer.parseInt(tokens[1]);
                System.out.print( chromosome + "\t" + position);

                location = new Location(position,position);
                iterator = featureList.selectOverlapping(chromosome, location, true).iterator();

                while(iterator.hasNext()){

                    feature = (FeatureI)iterator.next();

                    gene = feature.getAttribute("gene");

                    gbkey = feature.getAttribute("gbkey");

                    System.out.print("\t" + gene);
                    System.out.print("\t|" + feature.type() + "|");

                }

                    System.out.println();

            }

        }catch(IOException ioException) {
                ioException.printStackTrace();
        } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }

    }

	
}
