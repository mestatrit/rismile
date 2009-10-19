package rismile.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Bin2c {

	
	static void printUsage()
	{
		System.out.println("Uasge: bin2c binfile outputfile");
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String outputfile = "output.c";
		
		System.out.println("Bin to C convert");
		if( args.length > 0 )
			System.out.println("Bin file name is:"+ args[0]);
		else
		{
			System.out.println("Missing Bin file");
			printUsage();
			return;
		}
		
		if( args.length > 1 )
		{
			outputfile = args[1];
		}
		System.out.println("output file name is:"+ outputfile);
		
		convert(args[0], outputfile);
	}

	static void convert(String inputfile, String outputfile)
	{
		File ifile = new File(inputfile);
		File ofile = new File(outputfile);

		try {
			FileInputStream fis = new FileInputStream(ifile);
			FileOutputStream ois = new FileOutputStream(ofile);
			try {
				ois.write("# hello world\r\n".getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			byte[] readbuf = new byte[24];
			
			try {
				int len;
				while( (len = fis.read(readbuf)) > 0 )
				{
					for( int loop = 0; loop < len ; loop++)
					{
						int b = readbuf[loop];
						b = b >= 0 ? b: (b+0x100);
						ois.write((b+",").getBytes());
					}
					
					ois.write(("\r\n").getBytes());
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			System.out.println("No input file "+ inputfile + " find.");
			e.printStackTrace();
		}
		System.out.println("File convert finished");
	}
	
}
