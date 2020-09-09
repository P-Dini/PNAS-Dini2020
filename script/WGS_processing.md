# Processing of WGS data.

## Mapping with bwa v0.7.17.
```
bwa mem GCA_002863925.1_EquCab3.0_genomic.fna 1.fq 2.fq > 1.sam
```

## Index with samtools.
```
samtools view -bT GCA_002863925.1_EquCab3.0_genomic.fa 1.sam > 1.bam
samtools sort 1.bam -o 1.bam
samtools index 1.bam
```

## Group reads by gDNA of origin using Piccard (GATK).
```
java -jar /home/pdi222/picard-2.9.0/picard.jar AddOrReplaceReadGroups I= Foal_1.bam O= Foal1_gDNA.grouped.bam RGID=4 RGLB=lib1 RGPL=illumina RGPU=unit1 RGSM=3_Foal1_DNA
samtools index Foal_1.grouped.bam

java -jar /home/pdi222/picard-2.9.0/picard.jar AddOrReplaceReadGroups I= Mare_1.bam O= Mare1_gDNA.grouped.bam RGID=4 RGLB=lib1 RGPL=illumina RGPU=unit1 RGSM=4_Mare1
samtools index Mare_1.grouped.bam

java -jar /home/pdi222/picard-2.9.0/picard.jar AddOrReplaceReadGroups I= Stallion_1.bam O= Stallion1_gDNA.grouped.bam RGID=4 RGLB=lib1 RGPL=illumina RGPU=unit1 RGSM=5_Stallion1
samtools index Stallion_1.grouped.bam

```

# Variant calling using GATK.
```
# Make genome for GATK.
java -jar /home/pdi222/picard-2.9.0/picard.jar CreateSequenceDictionary R= GCA_002863925.1_EquCab3.0_genomic.fna O= EquCab3.0_genomic.dict
samtools faidx EquCab3.0_genomic.fna

# Variant calling.
java -jar /home/pdi222/GATK/GenomeAnalysisTK.jar -T UnifiedGenotyper -I Foal1_RNA.F1R2.sorted.grouped.bam -I Foal1_RNA.F2R1.sorted.grouped.bam -I Foal1_gDNA.grouped.bam -I Mare1_gDNA.grouped.bam -I Stallion1_gDNA.grouped.bam -R EquCab3.0_genomic.fna -U ALLOW_N_CIGAR_READS  --out 	Trio1.vcf  --genotype_likelihoods_model BOTH -rf ReassignMappingQuality -DMQ 60
```
