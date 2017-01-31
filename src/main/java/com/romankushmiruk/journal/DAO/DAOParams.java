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
