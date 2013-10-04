package HydroPower;

public class hydroPower {
	
	
	 

	 
	 
	 private double height;

	 private double volume_water;

	 private int timestep;
	 
	 private double max_volume_water_per_secondHEC0=1.5; //from ivanco calculations
	 private double max_volume_water_per_secondHEC1=1.5;
	 private double max_volume_water_per_secondHEC2=2.1;
	 private double max_volume_water_per_secondHEC3=1.8;
	 private double max_volume_water_per_secondHEC6=0.14;
	 
	 
	 private double Gen0=8; //from ivanco calculations
	 private double Gen1=8;
	 private double Gen2=8.35;
	 private double Gen3=8.35;
	 private double Gen6=7;

	 double [] powerGenerated;
	 double  total_power_Generated;	 
	 
	 private double middleHigh_zletovo;
	 private double middleLozZ_UpZ;
	 private double Qprobisthip;
	 private double QUpperZone;
	 private double QEcology;
	 
	 public void setInput(double height,  double volume_water, double middleHigh_Knezevo, double middleLowZ_UpZ,double Qprobishtip, 
			 double QUpperZone, double QEcology, int timestep ) {
			this.height=height;
			this.volume_water=volume_water;
			this.timestep=timestep;
			this.middleHigh_zletovo=middleHigh_Knezevo;
			this.middleLozZ_UpZ=middleLowZ_UpZ;
			this.Qprobisthip=Qprobishtip;
			this.QUpperZone=QUpperZone;
			this.QEcology=QEcology;
			
			
		}
	 

	 boolean print1 = false;
	 
	 
	 
	
	 public void calculate ()
	 
	 {
		 int month=timestep%12;
		 int [] year = {31,28,31,30,31,30,31,31,30,31,30,31};
		 
		 double volume_water_per_secondHEC0=(volume_water*Math.pow(10, 6))/(year[month]*86400);
if (print1)		 System.out.println("volume water  "+volume_water+" water per second  "+volume_water_per_secondHEC0);
		 
		 
		 int a=1;
		 
		 double [] powerGenerated= new double [5];
		
		 //knezevo HEC0
		 if (volume_water_per_secondHEC0>max_volume_water_per_secondHEC0) volume_water_per_secondHEC0=max_volume_water_per_secondHEC0;
		 // knezevo generator
		 powerGenerated[0]= a*volume_water_per_secondHEC0*height*year[month]*24*Gen0;
		 
if (print1) System.out.println("volume water per second HEC0  "+volume_water_per_secondHEC0 +" height "+height+ "year month "+year[month] + "power Generated "+powerGenerated[0] );	 
		 
		 // HEC1
		// double QEcologyMS=(QEcology*Math.pow(10, 6))/(year[month]*86400);
		 double volume_water_per_secondHEC1=volume_water_per_secondHEC0-0.05;
			 if(volume_water_per_secondHEC0<0)volume_water_per_secondHEC0=0;
		 if (volume_water_per_secondHEC1>max_volume_water_per_secondHEC1) volume_water_per_secondHEC1=max_volume_water_per_secondHEC1;
		 powerGenerated[1]= a*volume_water_per_secondHEC1*(990-820)*year[month]*24*Gen1;
		 
if (print1) System.out.println("volume water per second HEC1  "+volume_water_per_secondHEC1+  "month "+year[month] + "power Generated "+powerGenerated[1] );	 
		 
		 //HEC2
		 double QProbisthipMS=(Qprobisthip*Math.pow(10, 6))/(year[month]*86400);
		 double QmiddleHigh_KnezevoMS=(middleHigh_zletovo*Math.pow(10, 6))/(year[month]*86400);
		 double volume_water_per_secondHEC2=volume_water_per_secondHEC1+QmiddleHigh_KnezevoMS-QProbisthipMS;
		 
		 if (volume_water_per_secondHEC2>max_volume_water_per_secondHEC2) volume_water_per_secondHEC2=max_volume_water_per_secondHEC2;
		 powerGenerated[2]=a * volume_water_per_secondHEC2*(820-620)*year[month]*24*Gen2;
		
if (print1) System.out.println("volume water per second HEC2  "+volume_water_per_secondHEC2+ " volume Probistip "+ QProbisthipMS+ 
		" volume QmiddleHigh_KnezevoMS "+  QmiddleHigh_KnezevoMS +"month "+year[month] + "power Generated "+powerGenerated[2] );	 
		 
		//HEC3
		 double middleLowZ_UpZMS=(middleLozZ_UpZ*Math.pow(10, 6))/(year[month]*86400);
		 double QUpperZoneMS=(QUpperZone*Math.pow(10, 6))/(year[month]*86400);
		 double volume_water_per_secondHEC3=volume_water_per_secondHEC2+middleLowZ_UpZMS-QUpperZoneMS;
		 
		 if (volume_water_per_secondHEC3>max_volume_water_per_secondHEC3) volume_water_per_secondHEC3=max_volume_water_per_secondHEC3;
		 powerGenerated[3]=a * volume_water_per_secondHEC3*(620-480)*year[month]*24*Gen3;

if (print1)		 System.out.println("volume water per second HEC3  "+volume_water_per_secondHEC3+ " volumemiddleLowZ_UpZMS "+ middleLowZ_UpZMS+ 
					" volume Qupperzone "+  QUpperZoneMS +"month "+year[month] + "power Generated "+powerGenerated[3] );
		//HEC6
		 
		 double volume_water_per_secondHEC6=QProbisthipMS;
		 if (volume_water_per_secondHEC6>max_volume_water_per_secondHEC6) volume_water_per_secondHEC6=max_volume_water_per_secondHEC6;
		 powerGenerated[4]=8.35 * volume_water_per_secondHEC6*(820-620)*year[month]*24*Gen6;
		 
if (print1) System.out.println("volume water per second HEC6  "+powerGenerated[4] ); 
	
		 this.powerGenerated=powerGenerated;
		 
		 double total_power_Generated=0;
		 
		 for(int i=0;i<5;i++) total_power_Generated+=powerGenerated[i];
		 total_power_Generated=total_power_Generated/1000000;
		 
if (print1) System.out.println("TOTAL POWER GENERATED  "+total_power_Generated ); 
		 this.total_power_Generated=total_power_Generated; // in megawats
		 
		// System.out.println("timestep " + timestep+ "  month "+month+"  days "+year[month]+ "volume water 10 ^ 6 "+  volume_water + " water volume per second "+volume_water_per_second+" height "+ height+
			//	" power knezevo "+powerGenerated[0]+" power zletovo 1-- "+ powerGenerated[1]+ " power zletovo 2-- " +powerGenerated[2]+
				//"power zletovo 3-- "+powerGenerated[3]+" power komplet" +total_power_Generated);
		 
	 }
	 
	 public double [] return_power_Generated(){ return powerGenerated;}
	 
	 public double return_total_power_Generated(){ return total_power_Generated;}
	

}
