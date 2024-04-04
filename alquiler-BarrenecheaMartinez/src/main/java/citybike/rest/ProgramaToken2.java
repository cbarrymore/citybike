package rest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class ProgramaToken2 {
	
	public static void main(String[] args) {
		String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYXVuQHVtLmVzIiwicm9sZXMiOiJwcm9mZXNvciIsImV4cCI6MTcwOTA2MDg5MX0.PEyyNLE8ib9l3vyz_JBpoUDSPC4GHnIngmAWXwGQvnI\r\n";
		
		Claims claims = Jwts.parser()
				.setSigningKey("secret")
				.parseClaimsJws(jwt)
				.getBody();
		System.out.println(claims.toString());

	}
}
