package edu.uky.gluck.ConservedRegionAnalysis;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import htsjdk.samtools.util.CloseableIterator;
import htsjdk.variant.variantcontext.Genotype;
import htsjdk.variant.variantcontext.VariantContext;
import htsjdk.variant.vcf.VCFFileReader;
import htsjdk.variant.vcf.VCFHeader;

public class ParseEc3_VCF_For_Twilight {

	public static void main(String[] args) {
		
		String TWILIGHT = "Twilight";
		String horse = "3519";
		int arrayLength = 100;
		int[] positions = getIntegerArray(arrayLength);
		
		VCFFileReader r=new VCFFileReader(new File("allEquids.Ec_build-3.0.realigned_chr11.vcf.gz") );
		
		VCFHeader vcfHeader = r.getFileHeader();
		
		
		String[] nonCabSampleNames = getNonCabSampleNames();

		ArrayList sampleNames = vcfHeader.getSampleNamesInOrder();
		
		String[] cabSampleNames = getCabSampleNames(TWILIGHT,sampleNames,nonCabSampleNames);
		
		int[] nonCabHistogram = getIntegerArray(2000);
		int[] cabHistogram = getIntegerArray(2000);
		
		
		CloseableIterator<VariantContext> t=r.iterator();
		Genotype genotype = null;
		int lastNCPosition = 0;
		int lastCPosition = 0;
		VariantContext ctx = null;
		int neighborDistance =0;
		
		while(t.hasNext()){
		    ctx =t.next();
		    genotype = ctx.getGenotype(TWILIGHT);
		    
		    if(genotype.isHomVar()) {
		    	continue;
		    }
		    
		    if(isHomozygousInNonCab(ctx,nonCabSampleNames)) {
		    	neighborDistance = Math.min(ctx.getStart()-lastNCPosition,nonCabHistogram.length-1);
		    	nonCabHistogram[neighborDistance]++;
		    	lastNCPosition=ctx.getStart();
		    	if(isVariantInCabs(ctx,cabSampleNames)) {
		    		neighborDistance = Math.min(ctx.getStart()-lastCPosition,cabHistogram.length-1);
		    		cabHistogram[neighborDistance]++;
			    	lastCPosition=ctx.getStart();
		    	}
		    	
		    }
		    
		}
		t.close();
		r.close();
		
		long nonCabHistogramTotal = getTotal(nonCabHistogram);
		long cabHistogramTotal = getTotal(cabHistogram);
		double[] cabProbability = new double[cabHistogram.length];
		double[] nonCabProbability = new double[nonCabHistogram.length];
		
		for(int i=0;i<nonCabHistogram.length;i++) {
			cabProbability[i] = (double)cabHistogram[i]/(double)cabHistogramTotal;
			nonCabProbability[i] = (double)nonCabHistogram[i]/(double)nonCabHistogramTotal;
			System.out.println(i + "\t" 
					+ cabProbability[i] + "\t" 
					+ nonCabProbability[i]);
		}
		
		//*****************************
		//VCFFileReader 
		r=new VCFFileReader(new File("allEquids.Ec_build-3.0.realigned_chr11.vcf.gz"));
		//CloseableIterator<VariantContext>
		t=r.iterator();

		neighborDistance =0;
		double val = 0.0;
		
		while(t.hasNext()){
		    ctx =t.next();
		    genotype = ctx.getGenotype(TWILIGHT);
		    
		    if(genotype.isHomVar()) {
		    	continue;
		    }
		    
		    if(isHomozygousRefInNonCab(ctx,nonCabSampleNames)) {

		    	if(ctx.getGenotype(horse).isHomVar()) {
		    		
			    	positions = addToPositions(positions,ctx.getStart());
			    	val = getMLE(500, positions, cabProbability, nonCabProbability);
			    	int i=0;
					for(;i<positions.length;i++) {
						if((positions[i]-positions[0])>500) {
							i--;
							break;
						}
					}
					if(i>=positions.length) {
						i--;
					}
					if(val>1000.0) {
					
						
						System.out.println("chr1:" + positions[0] + "-" + positions[i] + "\t" + val + "\tchr1:" + positions[0] + "-" + positions[i]);
									   
					}
		    	}
		    	
		    }
		    
		}
		t.close();
		r.close();
		
	}
	
	private static String[] getNonCabSampleNames() {

		String[] nonCabSampleNames = {"D_1989","D_3611","SomaliAss","boehmi","grevyi","kiang","onager","quagga","zebra"};
		return nonCabSampleNames;
	}
	
	private static String[] getCabSampleNames(String Twilight,ArrayList sampleNames,String[] nonCabSampleNames) {

		HashMap hashMap = new HashMap();
		for(int i=0;i<nonCabSampleNames.length;i++) {
			hashMap.put(nonCabSampleNames[i], null);
		}

		int cabSampleCount = sampleNames.size() - nonCabSampleNames.length - 1; //The "- 1" is for Twilight
		
		String[] cabSampleNames = new String[cabSampleCount];
		String sampleName = null;
		int index = 0;
		
		for(int i=0;i<sampleNames.size();i++) {
			
			sampleName = ((String)sampleNames.get(i)).trim();
			if(!hashMap.containsKey(sampleName) && !sampleName.equals(Twilight)) {
				cabSampleNames[index]=sampleName;
				index++;
			}
			
		}
		
		return cabSampleNames;
	}
	private static boolean isHomozygousRefInNonCab(VariantContext ctx,String[] nonCabSampleNames) {
		
		Genotype genotype = null;
		int cabCount = 0;
		int nonCabCount = 0;
		for(int i=0;i<nonCabSampleNames.length;i++) {
			
			genotype = ctx.getGenotype(nonCabSampleNames[i]);
			if(genotype.getGenotypeString().equals("*/*") || genotype.getGenotypeString().equals("./.")) {
				continue;
			}
			
			if(!genotype.isHomRef()) {
				cabCount++;
				//System.out.println("*****" + nonCabSampleNames[i] + "\t" + genotype.getGenotypeString());
				//System.out.flush();
			}else {
				nonCabCount++;
			}
				
		}
		
		if(nonCabCount>0.75*nonCabSampleNames.length) {
			return true;
		}else{
			return false;
		}
		
	}
	
private static boolean isHomozygousInNonCab(VariantContext ctx,String[] nonCabSampleNames) {
		
		Genotype genotype = null;
		int cabCount = 0;
		int nonCabCount = 0;
		for(int i=0;i<nonCabSampleNames.length;i++) {
			
			genotype = ctx.getGenotype(nonCabSampleNames[i]);
			if(genotype.getGenotypeString().equals("*/*") || genotype.getGenotypeString().equals("./.")) {
				continue;
			}
			
			if(!genotype.isHomVar()) {
				cabCount++;
				//System.out.println("*****" + nonCabSampleNames[i] + "\t" + genotype.getGenotypeString());
				//System.out.flush();
			}else {
				nonCabCount++;
			}
				
		}
		
		if(nonCabCount>0.75*nonCabSampleNames.length) {
			return true;
		}else{
			return false;
		}
		
	}
	
	
	private static boolean isVariantInCabs(VariantContext ctx,String[] cabSampleNames) {
		
		Genotype genotype = null;
		for(int i=0;i<cabSampleNames.length;i++) {
			
			genotype = ctx.getGenotype(cabSampleNames[i]);
			if(genotype.getGenotypeString().equals("*/*") || genotype.getGenotypeString().equals("./.")) {
				continue;
			}
			
			if(!genotype.isHomRef()) {
				//System.out.println(cabSampleNames[i] + "\t" + genotype.getGenotypeString());
				//System.out.flush();
				return true;
			}
			
		}
		return false;
	}
	
	private static int[] getIntegerArray(int size) {
		
		int[] intArray = new int[size];
		for(int i=0;i<intArray.length;i++) {
			intArray[i] = 0;
		}
		return intArray;
	}
	
	private static long getTotal(int[] array) {
		
		long total = 0;
		for(int i=0;i<array.length;i++) {
			total+=array[i];
		}
		return total;
	}
	
	private static int[] addToPositions(int[] positions, int position) {
		
		for(int i=0;i<positions.length;i++) {
			
			if(positions[i]==0) {
				positions[i] = position;
				return positions;
			}
		}
		
		int[] newArray = new int[positions.length];
		
		int i=0;
		for(i=1;i<positions.length;i++) {
			newArray[i-1]=positions[i];
		}
		newArray[i-1] = position;
		
		return newArray;
	}
	
	private static double getMLE(int windowLength, int[] positions, double[] ancestralProb, double[] nonCabProb) {
		
		double mle = 1.0;
		
		int index = 1;
		
		while(index<positions.length && (positions[index]-positions[0])<=windowLength) {
			
			if(ancestralProb[index]<=0.0) {
				return 0.0;
			}
			
			mle*=nonCabProb[index]/ancestralProb[index];
			index++;
		}
		
		return mle;
	}
	
}
