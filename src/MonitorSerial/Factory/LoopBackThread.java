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
package MonitorSerial.Factory;

import MonitorSerial.Controle.ControleMonitor;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fmaia
 */
public class LoopBackThread implements Runnable{
    
    private Boolean stop;
    private static Integer tempo = 1000;
    private final ControleMonitor controleMonitor;

    public LoopBackThread(ControleMonitor controleMonitor) {
        this.controleMonitor = controleMonitor;
    }

    public void setTempo(Integer tempo) {
        this.tempo = tempo;
    }

    public static Integer getTempo() {
        return tempo;
    }

    public void stop() {
        System.out.println("Entrou no Stop");
        this.stop = true;
    }
    
    public void iniciarLoopBack() throws InterruptedException{
        this.stop = false;
        while(!stop){
            this.controleMonitor.enviarTexto(randomico());
            Thread.sleep(tempo);
        }
    }
    
    @Override
    public void run() {
        try {
            this.controleMonitor.setTempoLoopBackGui(tempo);
            this.iniciarLoopBack();
        } catch (InterruptedException ex) {
            Logger.getLogger(LoopBackThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String randomico() {
  
    int leftLimit = 65; // letter 'a'
    int rightLimit = 90; // letter 'z'
    int targetStringLength = 10;
    Random random = new Random();
    StringBuilder buffer = new StringBuilder(targetStringLength);
    for (int i = 0; i < targetStringLength; i++) {
        int randomLimitedInt = leftLimit + (int) 
          (random.nextFloat() * (rightLimit - leftLimit + 1));
        buffer.append((char) randomLimitedInt);
    }
    return buffer.toString()+"\n";
}
    
}
