package core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ThreadCopy extends Thread{
	
	InputStream is;
	OutputStream os;
	
	public ThreadCopy(InputStream isSetup, OutputStream osSetup){
		is = isSetup;
		os = osSetup;
	}
	
	public void run() {
		int i;
	    byte[] b = new byte[1024];
	    try {
			while((i=is.read(b))!=-1) {
			    os.write(b, 0, i);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    System.out.println(Thread.currentThread().getName());
    }

}
