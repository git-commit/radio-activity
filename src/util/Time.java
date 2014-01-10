/*******************************************************************************
 * Copyright (c) 2013 Yannic Remmet, Maximilian Berger.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yannic Remmet - initial API and implementation
 *     Maximilian Berger - initial API and implementation
 ******************************************************************************/
package util;
import java.security.InvalidParameterException;

import com.google.common.collect.ImmutableSet;



public class Time {
	private int nanoseconds;
	private int microseconds;
	private int milliseconds;
	private int seconds;
	private int minutes;
	private int hours;
	
	public static final ImmutableSet<String> validTimeUnits = ImmutableSet.of("nanoseconds","ns","microseconds","us","milliseconds","ms","seconds","s","minutes","m","hours","h","days","d");

	/**@param unit Valid: nanoseconds,ns,microseconds,us,milliseconds,ms,seconds,s,minutes,m,hours,h,days,d*/
	public Time(long time, String unit){
		if(!Time.validTimeUnits.contains(unit)){
			throw new InvalidParameterException("Wrong time unit!");
		}else{
			switch (unit){
			case "nanoseconds":
			case "ns":
				nanoseconds = ((int) (time-((time = (time / 1000))*1000)));
				
			case "microseconds" :
			case "us":
				microseconds = (int) (time-((time = (time / 1000))*1000));

			case "milliseconds":
			case "ms":
				milliseconds = (int) (time-((time = (time / 1000))*1000));

			case "seconds":
			case "s":
				seconds = (int) (time-((time = (time / 60))*60));

			case "minutes":
			case "m":
				minutes = (int) (time-((time = (time / 60))*60));

			case "hours":
			case "h":
				hours = (int) (time-((time = (time / 24))*24));

			case "days":
			case "d":
				hours = (int) (time*24);
			}
		}
	}
	
	public Time(long time){ //in milliseconds
		this(time,"ms");
	}
	
	public Time sub(Time other){
		//TODO Implement
		return null;
	}
	
	public Time add(Time other){
		//TODO Implement
		return null;
	}
	


	public int getNanoseconds() {
		return nanoseconds;
	}

	public int getMicroseconds() {
		return microseconds;
	}

	public int getMilliseconds() {
		return milliseconds;
	}

	public int getSeconds() {
		return seconds;
	}

	public int getMinutes() {
		return minutes;
	}

	public int getHours() {
		return hours;
	}
	
	public int getCompleteMilliseconds(){
		int result = milliseconds; //always round down

		result += seconds * 1000;
		result += minutes * 60000;
		result += hours * 60000000;

		return result;
	}
	
	public String toString(){
		return String.format("%d Hours, %d Minutes, %d Seconds, %d Milliseconds, %d Microseconds, %d Nanoseconds",hours,minutes,seconds,milliseconds,microseconds,nanoseconds);
	}
	
	public int toFrames(){
		return (getCompleteMilliseconds() / 26);
	}
	
	public static int millisecondsFromFrames(int frames){
		return frames*26;
	}
}

