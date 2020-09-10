package edu.louisville.cgemm.pouyasproject;

import java.io.IOException;
import java.util.StringTokenizer;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

public class CalculateXCoverage
{
    public static void main(final String[] args) {
        try {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(args[0])));
            final int binSize = 100000;
            final int[][] bins = new int[1250][4];
            int bin = 0;
            String line = null;
            StringTokenizer st = null;
            int position = 0;
            int depth = 0;
            while ((line = bufferedReader.readLine()) != null) {
                st = new StringTokenizer(line);
                st.nextToken();
                position = Integer.parseInt(st.nextToken());
                st.nextToken();
                bin = position / binSize;
                for (int i = 0; i < 4; ++i) {
                    depth = Integer.parseInt(st.nextToken());
                    final int[] array = bins[bin];
                    final int n = i;
                    array[n] += depth;
                    st.nextToken();
                    st.nextToken();
                }
            }
            bufferedReader.close();
            for (int i = 0; i < bins.length; ++i) {
                System.out.print(i * binSize);
                for (int j = 0; j < bins[i].length; ++j) {
                    System.out.print("\t" + bins[i][j] / binSize);
                }
                System.out.println();
            }
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}