/*
 * Aaron O'Connor
 * COMP40660 - Advances in Wireless Networking
 * Assignment 1

• Calculate the actual throughput for 802.11a/g/n/ac for all available data rates, and for both UDP and TCP.
• Your Program should accept 3 arguments:
• Protocol (UDP/TCP)
• Data rate (54, 48,36,...)
• Standard (802.11a, 802.11g, 802.11n, 802.11ac_w1 or
802.11ac_w2)
• Program must return for each scenario:
• The actual throughput [Mbps]
• The amount of time needed to transfer 10 GB of data.
 */
package throughputcalculator;
import javax.swing.*;

/**
 *
 * @author aaronoc
 */
public class ThroughputCalculator {
    
    public static void main(String[] args) {
        
        String protocol = JOptionPane.showInputDialog("Please Enter Network Protocol (UDP/TCP):");
        double rate = Double.parseDouble(JOptionPane.showInputDialog("Please Enter Network Data Rate (54, 48,36,...)"));
        String standard = JOptionPane.showInputDialog("Please Enter Network Standard (802.11a, 802.11g, 802.11n, 802.11ac_w1 or\n802.11ac_w2)");
        
        int bits = 0;
        double time = 0.0;
        
        if(standard.equals("802.11n") || standard.equals("802.11ac_w1") || standard.equals("802.11ac_w2")){
            bits = 8 * (1542 + 6) + 6;  // Add on extra bits for MAC header and tail
        }
        else if(standard.equals("802.11a") || standard.equals("802.11g")){
            bits = 8 * (1542) + 6;  //Convert to bits and add on OFDM tail.
        }
        else{
            System.out.println("Sorry, " + standard + " is not a valid standard.");
            System.exit(0);
        }
        
        int symbols = bits / getBitsPerSymbol(rate, standard);    //Get bits per symbol for the data rate
        double sDur = getSymbolDur(standard);
        double dataFrameTime = symbols * sDur;    
        double preamble = getPreamble(standard);
        int sifs = getSIFS(standard);
        int slotTime = 9;
        int difs = (2*slotTime) + sifs;
        int ACK = 4;    //1 symbol (only 14 bytes) => 4 μs
        int tcpACK = 24; //time taken to transmit 40 bytes
        
        if(protocol.equalsIgnoreCase("UDP")){   //Perform UDP time calculation
            time = difs + preamble + dataFrameTime + sifs + preamble + ACK;
        }
        
        else if(protocol.equalsIgnoreCase("TCP")){  //Perform TCP time calculation
            time = difs + dataFrameTime + sifs + ACK + difs + tcpACK + sifs + ACK;
        }
        else{
            System.out.println("Sorry, " + protocol + " is not a valid protocol.");
            System.exit(0);
        }
        
        double throughput = convertToMbps(time, bits);
        double tenGB = 10 / (throughput / 8000);
        
        
        //PRINT STATS
        System.out.println("Protocol:\t\t"+protocol+"\nData Rate:\t\t"+rate+"\nNetwork Standard:\t"+standard);
        System.out.println("\nBits:\t\t\t"+bits+"\nSymbols:\t\t"+symbols+"\nSymbol Duration:\t"+sDur+"\nData Frame Time:\t"+dataFrameTime);
        System.out.println("\nPreamble\t\t"+preamble+"\nSIFS:\t\t\t"+sifs+"\nDIFS:\t\t\t"+difs);
        System.out.println("\n---------------\n\nTime Taken:\t\t"+Double.toString(time));
        System.out.println("\n---------------\n\nThroughput:\t\t"+String.format("%.2f", throughput) + "Mbps");
        System.out.println("\nTime to send 10GB:\t"+String.format("%.0f", tenGB) + " Seconds\n");
        
    }
    
    public static double convertToMbps(double time, int bits){
        //System.out.println(bits)
        bits = (1500 * 8) - (20 * 8) - (8*8);
        double sec = time * 0.000001;
        double mb = bits * 0.000001;
        return mb/sec;
    }
    
    public static double getPreamble(String standard){
        double preamble;
        
        if(standard.equals("802.11a") || standard.equals("802.11g")){
            preamble = 20.0;
        }
        else if(standard.equals("802.11n")){
            preamble = 46.0;
        }
        
        else if(standard.equals("802.11ac_w1")){
            
                preamble = 56.8;
        }
        else{
            preamble = 92.8;
        }
        
        return preamble;
    }
    
    public static int getSIFS(String standard){
        int sifs;
        if(standard.equals("802.11a") || standard.equals("802.11n") || standard.equals("802.11ac_w1")|| standard.equals("802.11ac_w2")){
            sifs = 16;
        }
        else{
            sifs = 10;
        }
        return sifs;
    }
    
    public static double getSymbolDur(String standard){
        double dur;
        if(standard.equals("802.11a") || standard.equals("802.11g")){
            dur = 4.0;
        }
        else{
            dur = 3.6;
        }
        return dur;
    }
    
    public static int getBitsPerSymbol(double dataRate, String standard){
        
        double bits = 0.0;
        
        if(standard.equals("802.11a") || standard.equals("802.11g")){
        
            int nChan = 48;

            if(dataRate == 6){
                bits = 1 * 0.5 * nChan;
            }
            else if(dataRate == 9){
                bits = 1 * 0.75 * nChan;
            }
            else if(dataRate == 12){
                bits = 2 * 0.5 * nChan;
            }
            else if(dataRate == 18){
                bits = 2 * 0.75 * nChan;
            }
            else if(dataRate == 24){
                bits = 4 * 0.5 * nChan;
            }
            else if(dataRate == 36){
                bits = 4 * 0.75 * nChan;
            }
            else if(dataRate == 48){
                bits = 6 * .6666 * nChan;
            }
            else if(dataRate == 54){
                bits = 6 * 0.75 * nChan;
            }
            else{
                System.out.println("Sorry, " + dataRate + " is not a valid data rate for " + standard);
                System.exit(0);
            }
        }
        else if (standard.equals("802.11n") || standard.equals("802.11ac_w1") || standard.equals("802.11ac_w2")){
            int nChan = 52;
            if(dataRate == 7.2){
                bits = 1 * 0.5 * nChan;
            }
            else if(dataRate == 14.4){
                bits = 2 * 0.5 * nChan;
            }
            else if(dataRate == 21.7){
                bits = 2 * 0.75 * nChan;
            }
            else if(dataRate == 28.9){
                bits = 4 * 0.5 * nChan;
            }
            else if(dataRate == 43.3){
                bits = 4 * 0.75 * nChan;
            }
            else if(dataRate == 57.8){
                bits = 6 * 0.66666 * nChan;
            }
            else if(dataRate == 65){
                bits = 6 * 0.75 * nChan;
            }
            else if(dataRate == 72.2){
                bits = 6 * 0.833333 * nChan;
            }
            else if(dataRate == 86.7){
                bits = 8 * 0.75 * nChan;
            }
            else if(dataRate == 96.3){
                bits = 8 * 0.833333 * nChan;
            }
            else{
                System.out.println("Sorry, " + dataRate + "is not a valid data rate for " + standard);
                System.exit(0);
            }
        }
        else{
            System.out.println("Sorry, " + standard + "is not a valid standard.");
            System.exit(0);
        }
        
        return (int)bits;
    }
    
}