package com.romankushmiruk.journal.DAO;

import org.springframework.stereotype.Component;

/**
 *
 * @author Roman
 */

@Component("DAOparams")
public class DAOParams {
        public String DRIVER="oracle.jdbc.driver.OracleDriver";
	public String URL="jdbc:oracle:thin:@netcracker.cb4jpppmphf8.us-west-2.rds.amazonaws.com:1522:ORCL";
	public String LOGIN="Ramiko";
	public String PASS="231094Fear";
}
