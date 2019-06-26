/*
    MonitorSerial - Comunocação serial com equipamnetos.
    Copyright (C) 2019 Fellipe Azevedo Porfirio Maia

    Este programa é software livre: você pode redistribuí-lo e / ou modificar
    sob os termos da Licença Pública Geral GNU, conforme publicada pela
    a Free Software Foundation, versão 3 da Licença, ou
    (a seu critério) qualquer versão posterior.

    Este programa é distribuído na esperança de que seja útil,
    mas SEM QUALQUER GARANTIA; sem mesmo a garantia implícita de
    COMERCIABILIDADE OU ADEQUAÇÃO A UM DETERMINADO FIM. Veja o
    GNU General Public License para mais detalhes.

    Você deveria ter recebido uma cópia da Licença Pública Geral GNU
    junto com este programa. Se não, veja <https://www.gnu.org/licenses/>.
    

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package MonitorSerial.Controle;

import MonitorSerial.Conexao.SerialCom;
import MonitorSerial.Conexao.SerialComLeitura;
import MonitorSerial.Factory.LoopBackThread;
import MonitorSerial.Gui.GuiContadorPacote;
import MonitorSerial.Gui.GuiMonitor;
import MonitorSerial.Gui.JFMonitorSerie;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fmaia
 */
public class ControleMonitor implements InterfaceMonitor{
    
    private SerialCom serialCom;
    private SerialComLeitura comunicacao;
    private final GuiMonitor guiMonitor;
    private ControleContadorPacotes controleContadorPacotes;
    private LoopBackThread loopback;
    private Thread threadEnvio;
    private ControleSerie controleSerie;
    
    public ControleMonitor (GuiMonitor guiMonitor){
        this.guiMonitor = guiMonitor;
        this.serialCom = new SerialCom();
        
    }
    
    public String[] getPortas(){
        return this.serialCom.ObterPortas();
    }
    
    public void startComunicacao(String porta, Integer Baudrate, Integer timeout) throws NoSuchPortException, PortInUseException, TooManyListenersException, UnsupportedCommOperationException{
        this.comunicacao = new SerialComLeitura(porta,
               Baudrate, timeout, this);
        //this.comunicacao.ObterIdDaPorta();
        this.comunicacao.AbrirPorta();
        try {
            this.comunicacao.startComunicacao();
        } catch (IOException ex) {
            Logger.getLogger(GuiMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void transmicao(String dados){
        this.comunicacao.transmicaoDados(dados);
        if(this.controleContadorPacotes != null){
            this.controleContadorPacotes.contarPacotesEnviados(dados);
        }
    }
    
    public void stopComunicacao(){
        this.comunicacao.fecharComunicacao();
    }
    
    public Integer[] getBaudrates(String porta){
        return this.serialCom.obterBaudrates(porta);
    }
    
    @Override
    public void rebimentoDados(String dados, String porta){
        this.guiMonitor.ajustarDados(dados);
        if(this.controleContadorPacotes != null){
            this.controleContadorPacotes.contarPacotesRecebido(dados);
        }
    }

    public void iniciarLoopBack() throws InterruptedException {
        this.loopback = new LoopBackThread(this);
        threadEnvio = new Thread(this.loopback);
        threadEnvio.start();
        System.out.println("Iniciado Thread");
    }
    
    public void paraLoopBack(){
        if(this.loopback != null){
            this.loopback.stop();
            this.threadEnvio.stop();
            System.out.println("Thread finalizada");
        }
    }

    public void enviarTexto(String randomico) {
        this.guiMonitor.enviarTexto(randomico);
        System.out.println("Envio da Mensagem \n-->" + randomico);
    }
    
    public void setTempoLoopBackGui(Integer tempo){
        this.guiMonitor.setTempoLoopBack(tempo);
    }
    
    public Integer getTempoLoopBack(){
        return this.loopback.getTempo();
    }

    public void newContadorPacotes() {
        if(this.controleContadorPacotes == null){
            this.controleContadorPacotes = new ControleContadorPacotes();
            this.controleContadorPacotes.setVisble(true,this.guiMonitor.getLocation());
        }else{
            this.controleContadorPacotes.setVisble(true,this.guiMonitor.getLocation());
        }
    }

    public void setVisibilidadeMonitorSerie() {
        this.controleSerie = new ControleSerie(this,this.guiMonitor.getLocation());
    }

    public void bloquearConexao(Boolean boo){
        this.guiMonitor.bloquearConexao(boo);
    }
}
