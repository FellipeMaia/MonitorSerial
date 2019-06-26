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

import MonitorSerial.Gui.GuiContadorPacote;
import java.awt.Point;
import java.util.Locale;

/**
 *
 * @author fmaia
 */
public class ControleContadorPacotes {
    
    
    private Long pacotesRecebidos; //RX
    private Long pacotesEnviados; //TX
    private Long pacotesPerdidos; //TX sem Confirmação de recebimento
    private Long pacotesLoopBack; //RX recebe o mesmo dado do TX enviou
    
    private Object ultimoPacotesRecebido;
    private Object ultimoPacotesEnviado;
    
    private final GuiContadorPacote guiContadorPacote;

    public ControleContadorPacotes() {
        this.guiContadorPacote = new GuiContadorPacote(this);
    }
    
    public void setVisble(Boolean boo,Point p){
        this.guiContadorPacote.setLocation(p);
        this.guiContadorPacote.setVisible(boo);
    }
    
    public void limparContagem(){
        this.pacotesRecebidos = Long.valueOf(0);
        this.pacotesEnviados  = Long.valueOf(0);
        this.pacotesPerdidos  = Long.valueOf(0);
        this.pacotesLoopBack  = Long.valueOf(0);
    }

    public Long getPacotesRecebidos() {
        return pacotesRecebidos;
    }

    public Long getPacotesEnviados() {
        return pacotesEnviados;
    }

    public Long getPacotesPerdidos() {
        return pacotesPerdidos;
    }

    public Long getPacotesLoopBack() {
        return pacotesLoopBack;
    }
    
    public void contarPacotesEnviados(Object object){
        this.ultimoPacotesEnviado = object;
        this.pacotesEnviados ++;
        this.guiContadorPacote.setJLTXText(pacotesEnviados);
    }
    
    public void contarPacotesRecebido(Object object){
        this.ultimoPacotesRecebido = object;
        if(object.equals(this.ultimoPacotesEnviado)){
            this.pacotesLoopBack ++;
            this.guiContadorPacote.setJLReturned(pacotesLoopBack);
        }else{
            this.pacotesRecebidos ++;
            this.guiContadorPacote.setJLRXText(pacotesRecebidos);
        }
    }
}
