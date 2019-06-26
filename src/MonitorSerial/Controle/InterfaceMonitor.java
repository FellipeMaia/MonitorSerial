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

/**
 *
 * @author fmaia
 */
public interface InterfaceMonitor {
    
    public void rebimentoDados(String object, String porta);
}
