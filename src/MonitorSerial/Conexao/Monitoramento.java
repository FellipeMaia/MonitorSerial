/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MonitorSerial.Conexao;

import MonitorSerial.Controle.InterfaceMonitor;
import MonitorSerial.Controle.ControleMonitor;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 *
 * @author fmaia
 */
public class Monitoramento implements SerialPortEventListener, Runnable{
    
    private String nomePorta;
    private SerialPort porta;
    private OutputStream saida;
    private InputStream entrada;
    private InterfaceMonitor interfaceMonitor;
    
    public Monitoramento(String nomePorta ,SerialPort porta, InterfaceMonitor interfaceMonitor) throws IOException{
        this.nomePorta = nomePorta;
        this.porta = porta;
        this.entrada = porta.getInputStream();
        this.saida = porta.getOutputStream();
        this.interfaceMonitor = interfaceMonitor;
    }
    
    public void EnviarUmaString(String msg){
        try {
            saida = porta.getOutputStream();
            System.out.println("FLUXO OK!");
        } catch (Exception e) {
            System.out.println("Erro.STATUS: " + e );
        }
        try {
            System.out.println("Enviando um byte para " + nomePorta );
            System.out.println("Enviando : " + msg );
            saida.write(msg.getBytes());
            Thread.sleep(100);
            saida.flush();
        } catch (Exception e) {
            System.out.println("Houve um erro durante o envio. ");
            System.out.println("STATUS: " + e );
            System.exit(1);
        }
    }
    
    @Override
    public void serialEvent(SerialPortEvent ev){       

        StringBuffer bufferLeitura = new StringBuffer();
        Byte [] readBuffer = {-1};
        
        int novoDado = 0;
        
        switch (ev.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
            break;
            case SerialPortEvent.DATA_AVAILABLE:
                //Novo algoritmo de leitura.
                while(novoDado != -1){
                    try{
                        novoDado = entrada.read();
                        if(novoDado == -1){
                            break;
                        }else if( '\n' == (char)novoDado){
                            break;
                        }else if('\r' == (char)novoDado){
                            bufferLeitura.append('\n');
                            break;
                        }else{
                            bufferLeitura.append((char)novoDado);
                        }
                    }catch(IOException ioe){
                        System.out.println("Erro de leitura serial: " + ioe);
                    }
                }
                this.interfaceMonitor.rebimentoDados(new String(bufferLeitura),this.nomePorta);
            break;
        }
    }

    @Override
    public void run(){
        try {
            Thread.sleep(5);
        } catch (Exception e) {
            System.out.println("Erro de Thred: " + e);
        }
    }
}