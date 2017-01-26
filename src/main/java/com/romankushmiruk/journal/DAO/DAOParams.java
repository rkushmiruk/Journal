/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.romankushmiruk.journal.DAO;

import org.springframework.stereotype.Component;

/**
 *
 * @author Roman
 */

@Component("DAOparams")
public class DAOParams {
        public String DRIVER="oracle.jdbc.driver.OracleDriver";
	public String URL="jdbc:oracle:thin:@localhost:1522:XE";
	public String LOGIN="Ramiko";
	public String PASS="231094";
}
