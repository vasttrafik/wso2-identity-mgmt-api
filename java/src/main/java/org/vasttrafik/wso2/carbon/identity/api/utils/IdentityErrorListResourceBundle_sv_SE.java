package org.vasttrafik.wso2.carbon.identity.api.utils;

import org.vasttrafik.wso2.carbon.common.api.utils.AbstractErrorListResourceBundle;

/**
 * @author Lars Andersson
 *
 */
public final class IdentityErrorListResourceBundle_sv_SE extends AbstractErrorListResourceBundle {

	private static Object[][] content = {
		// Identity server defined error codes
		{"17001","Felaktigt användarnamn","Användaren {0} existerar inte. Ange korrekt användarnamn och försök igen."},
		{"17002","Felaktigt lösenord","Det angivna lösenordet är felaktigt. Ange korrekt lösenord och försök igen."},
		{"17003","Kontot är låst","Användarkontot är låst och mäste låsas upp."},
		{"18001","Felaktig kod","Den angivna koden är felaktig. Ange korrekt kod och försök igen."},
		{"18002","Ogiltig nyckel/kod","Den angivna nyckeln/koden är inte längre giltig."},
		{"18003","Felaktigt användarnamn","Användaren {0} existerar inte. Ange korrekt användarnamn och försök igen"},
		{"18004","Felaktigt svar","Det angivna svaret {0} är felaktigt. försök igen."},
		{"18013","Felaktig kod","Den angivna koden är felaktig. Ange korrekt kod och försök igen."},
		// Custom error codes
		{"1000","Ogiltigt parametervärde","Det angivna värdet {0} för parametern {1} är ogiltigt."},
		{"1001","Resursen hittades inte","Den angivna resursen {0} hittades inte."},
		{"1002","Upptaget användarnamn","Användarnamnet {0} är upptaget. Var vänlig välj ett annat."},
		{"1003","Ogiltig token","Angiven token är inte längre giltig. Var vänlig logga in på nytt."},
		{"1004","Felaktig token","Angiven token är felaktig. Var vänlig ange en korrekt token."},
		{"1005","Felaktig token","Angiven token tillhör en annan användare. Var vänlig ange en korrekt token."},
		{"1006","Felaktigt användarnamn/lösenord","Angivet användarnamn/lösenord felaktigt. Var vänlig försök igen."},
		{"1007","Det finns redan ett konto med samma mailadress","Angiven mailadress används redan av ett annat konto. Var vänlig ange en annan adress."},
		{"1008","Hemlig fråga saknas","Användaren {0} saknar hemlig fråga."}
	};
	
	@Override
	protected Object[][] getContents() {
		return content;
	}
}
