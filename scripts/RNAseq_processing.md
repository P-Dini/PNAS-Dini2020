# Processing of raw RNA-seq reads.

## Trimming using TrimGalore v0.4.3
```
trim_galore --fastqc --paired --quality 30 --output_dir <> --adapter AGATCGGAAGAGCACACGTCTGAACTCCAGTCACNNNNNNATCTCGTATGCCGTCTTCTGCTTG  --adapter2 AGATCGGAAGAGCGTCGTGTAGGGAAAGAGTGTAGATCTCGGTGGTCGCCGTATCAT 1.fastq 2.fastq
```

## Mapping using STAR v2.5.2

```
# Make genome reference.
STAR --runMode genomeGenerate --genomeDir <>  --genomeFastaFiles GCA_002863925.1_EquCab3.0_genomic.fna

# Mapping of trimmed reads.
STAR --genomeDir GCA_002863925.1_EquCab3.0_genomic.fa --outSAMtype BAM SortedByCoordinate --outFileNamePrefix --readFilesIn <trimmed.1.fq> <trimmed.2.fq> --outFilterMismatchNmax 5 --outReadsUnmapped Fastx.
```

## Phasing of mapped RNA-seq reads using samtools v1.3.1

```
samtools view -b -h 1.bam -f 64 | samtools view -h  - -f 32 > 1.F1R2.sam 
samtools view -b -h 1.bam -f 128 | samtools view  - -f 16 >> 1.F1R2.sam
samtools view -b -h 1.bam -f 128 | samtools view -h  - -f 32 > 1.F2R1.sam 
samtools view -b -h 1.bam -f 64 | samtools view  - -f 16 >> 1.F2R1.sam


samtools view -S -b 1.F1R2.sam > 1.F1R2.bam
samtools view -S -b 1.F2R1.sam > 1.F2R1.bam


samtools sort 1.F1R2.bam -o 1.F1R2.sorted.bam
samtools sort 1.F2R1.bam -o 1.F2R1.sorted.bam

```

## Group the RNA-seq reads for each offspring using Piccard v2.9.0 (GATK).

```
java -jar /home/pdi222/picard-2.9.0/picard.jar AddOrReplaceReadGroups I= 1.F1R2.sorted.bam O= Foal1_RNA.F1R2.sorted.grouped.bam RGID=4 RGLB=lib1 RGPL=illumina RGPU=unit1 RGSM=1_Foal1_RNA_F1R2
java -jar /home/pdi222/picard-2.9.0/picard.jar AddOrReplaceReadGroups I= 1.F2R1.sorted.bam O= Foal1_RNA.F2R1.sorted.grouped.bam RGID=4 RGLB=lib1 RGPL=illumina RGPU=unit1 RGSM=2_Foal1_RNA_F2R1

```

## Index bams using samtools.

```
samtools index Foal1_RNA.F1R2.sorted.grouped.bam
samtools index Foal1_RNA.F2R1.sorted.grouped.bam

```




