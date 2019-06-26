/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MonitorSerial.Conexao;


import gnu.io.CommPortIdentifier;
import java.util.Enumeration;
/**
 *
 * @author fmaia
 */
public class SerialCom {
    
    protected String[] portas;
    protected Enumeration listaDePortas;

    public SerialCom(){
        listaDePortas = CommPortIdentifier.getPortIdentifiers();
        
        this.ListarPortas();
    }
    
    public String[] ObterPortas(){  
        return portas;  
    }
    
    public Integer[] obterBaudrates(String porta){
        //melhoria a ser implementada
        Integer[] baudrates = {300,1200,2400,4800,9600,19200,38400,57600,115200};
        return baudrates;
    }
    
    protected void ListarPortas(){
        int i = 0;
        portas = new String[10];
        while (listaDePortas.hasMoreElements()) {
            CommPortIdentifier ips =
            (CommPortIdentifier)listaDePortas.nextElement();
            portas[i] = ips.getName();
            i++;
        }
    }
    
    public boolean PortaExiste(String COMp){
        String temp;
        boolean e = false;
        while (listaDePortas.hasMoreElements()) {
            CommPortIdentifier ips = (CommPortIdentifier)listaDePortas.nextElement();
            temp = ips.getName();
            if (temp.equals(COMp)== true) {
                e = true;
            }
        }
        return e;
    }
    
}
