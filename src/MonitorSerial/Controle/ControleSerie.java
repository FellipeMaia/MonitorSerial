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
import MonitorSerial.Gui.JFMonitorSerie;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import java.awt.Point;
import java.io.IOException;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fmaia
 */
public class ControleSerie implements InterfaceMonitor{
    
    private SerialCom serialCom;
    private SerialComLeitura comunicacao1;
    private SerialComLeitura comunicacao2;
    private ControleMonitor controleMonitor;
    private JFMonitorSerie jFMonitorSerie;
    private String portaConexao1;

    ControleSerie(ControleMonitor controleMonitor, Point p) {
        this.serialCom = new SerialCom();
        this.controleMonitor = controleMonitor;
        this.jFMonitorSerie = new JFMonitorSerie(this);
        this.jFMonitorSerie.setLocation(p.x+40, p.y+40);
        this.jFMonitorSerie.setVisible(true);
    }

    @Override
    public void rebimentoDados(String dados,String porta) {
        if(porta.equals(this.portaConexao1)){
            if(this.jFMonitorSerie.isSelectLinenBreak1())
                dados += "\n";
            this.comunicacao2.transmicaoDados(dados);
        }else{
            if(this.jFMonitorSerie.isSelectLinenBreak2())
                dados += "\n";
            this.comunicacao1.transmicaoDados(dados);
        }
        this.controleMonitor.rebimentoDados((porta + " - " + dados),porta);
    }

    public Integer[] getBaudrates(String porta) {
        return this.serialCom.obterBaudrates(porta);
    }

    public String[] getPortas() {
        return this.serialCom.ObterPortas();
    }
    
    public void stopComunicacao(){
        this.comunicacao1.fecharComunicacao();
        this.comunicacao2.fecharComunicacao();
        this.controleMonitor.bloquearConexao(false);
    }
    
    public void startComunicacao(String[] porta, Integer[] Baudrate, Integer[] timeout) throws NoSuchPortException, PortInUseException, TooManyListenersException, UnsupportedCommOperationException{
        this.comunicacao1 = new SerialComLeitura(porta[0],Baudrate[0], timeout[0], this);
        this.comunicacao1.AbrirPorta();
        this.portaConexao1 = porta[0];
        this.comunicacao2 = new SerialComLeitura(porta[1],Baudrate[1], timeout[1], this);
        this.comunicacao2.AbrirPorta();
        try {
            this.comunicacao1.startComunicacao();
            this.comunicacao2.startComunicacao();
            this.controleMonitor.bloquearConexao(true);
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
}
