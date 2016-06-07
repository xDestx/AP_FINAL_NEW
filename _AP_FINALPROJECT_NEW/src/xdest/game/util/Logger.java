package xdest.game.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;

public class Logger {

	private String fileName;
	private ArrayList<String> messages;
	private int ticks;
	private final int maxTicks;
	
	/**
	 * @param max Max logs before auto save
	 */
	//Logs stuff
	public Logger(String title, int max) {
		Calendar c = Calendar.getInstance();
		fileName = (c.get(Calendar.MONTH)+1) + "." + c.get(Calendar.DAY_OF_MONTH) + "." + c.get(Calendar.YEAR) + "." + c.get(Calendar.HOUR_OF_DAY) + "." + c.get(Calendar.MINUTE) + "." + c.get(Calendar.SECOND);
		messages = new ArrayList<String>();
		if (title == null)
			this.log("Log file created for " + fileName + " m.d.y.h.m.s");
		else
			this.log("Log file created for " + fileName + " m.d.y.h.m.s for [" + title + "]");
		this.maxTicks = max;
		ticks = maxTicks;
	}
	
	
	public void log(String message)
	{
		Calendar c = Calendar.getInstance();
		String full = "[" + ((c.get(Calendar.HOUR_OF_DAY) < 10) ? "0"+c.get(Calendar.HOUR_OF_DAY):c.get(Calendar.HOUR_OF_DAY)) + ":" + ((c.get(Calendar.MINUTE) < 10) ? "0"+c.get(Calendar.MINUTE):c.get(Calendar.MINUTE)) + ":" + ((c.get(Calendar.SECOND) < 10) ? "0"+c.get(Calendar.SECOND):c.get(Calendar.SECOND)) + "] " + message;
		messages.add(full);
		System.out.println(full);
		ticks--;
		if(ticks <= 0)
		{
			ticks = maxTicks;
			save();
		}
	}
	
	
	
	
	public void save()
	{
		File f = new File(System.getProperty("user.home") + "/Desktop/SJFAXVII/logs/");
		File f1 = new File(System.getProperty("user.home") + "/Desktop/");
		if(!f.exists())
			f.mkdirs();
		if(!f1.exists())
			f1.mkdirs();
		f = new File(f.getPath() + "/" + fileName + ".txt");
		try {
			PrintWriter fos = new PrintWriter(new FileOutputStream(f));
			for (int i = 0; i < messages.size(); i++)
			{
				fos.write(messages.get(i));
				fos.write("\n");
			}
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		f1 = new File(f1.getPath() + "/" + fileName + ".txt");
		try {
			PrintWriter fos = new PrintWriter(new FileOutputStream(f1));
			for (int i = 0; i < messages.size(); i++)
			{
				fos.write(messages.get(i));
				fos.write("\n");
			}
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
