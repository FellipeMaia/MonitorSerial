/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MonitorSerial.Conexao;

import MonitorSerial.Controle.ControleMonitor;
import MonitorSerial.Controle.InterfaceMonitor;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

/**
 *
 * @author fmaia
 */
public class SerialComLeitura{
    public String Dadoslidos;
    public int nodeBytes;
    private int baudrate;
    private int timeout;
    private CommPortIdentifier cp;
    private SerialPort porta;
    private Thread threadLeitura;
    private String nomePorta;
    private InterfaceMonitor interfaceMonitor;
    private Monitoramento moni;
    
    public SerialComLeitura( String p , int b , int t ,InterfaceMonitor interfaceMonitor){
        this.nomePorta = p;
        this.baudrate = b;
        this.timeout = t;
        this.interfaceMonitor = interfaceMonitor;
    }
    
    public void ObterIdDaPorta() throws NoSuchPortException{
        cp = CommPortIdentifier.getPortIdentifier(nomePorta);
    }

    public void AbrirPorta() throws PortInUseException, UnsupportedCommOperationException, NoSuchPortException{
        cp = CommPortIdentifier.getPortIdentifier(nomePorta);
        porta = (SerialPort)cp.open("SerialComLeitura", timeout);
        //configurar parâmetros
        porta.setSerialPortParams(baudrate,
        SerialPort.DATABITS_8,
        SerialPort.STOPBITS_1,
        SerialPort.PARITY_NONE);
        porta.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
    }
    
    public void startComunicacao() throws IOException, TooManyListenersException{
        this.moni = new Monitoramento(nomePorta, porta, interfaceMonitor);
        porta.addEventListener(moni);
        porta.notifyOnDataAvailable(true);
        threadLeitura = new Thread(moni);
        threadLeitura.start();
        moni.run();
    }
    
    public void transmicaoDados(String dados){
        this.moni.EnviarUmaString(dados);
    }
    
    public void fecharComunicacao(){
            porta.close();
    }
    
    public String obterPorta(){
        return nomePorta;
    }

    public int obterBaudrate(){
        return baudrate;
    }
    
    
    
}
