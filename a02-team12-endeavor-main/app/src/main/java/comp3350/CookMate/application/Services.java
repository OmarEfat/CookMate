package comp3350.CookMate.application;

import comp3350.CookMate.persistence.AccountManagement;
import comp3350.CookMate.persistence.HSQLDB.AccountManagementHSQLDB;
import comp3350.CookMate.persistence.HSQLDB.RecipeManagementHSQLDB;
import comp3350.CookMate.persistence.RecipeManagement;

public class Services
{
    private static String dbName = "SC";
    private static AccountManagement accountManagement = null;
    private static RecipeManagement recipeManagement = null;

    public static void setDBPathName( final String name )
    {
        try
        {
            Class.forName( "org.hsqldb.jdbcDriver" ).newInstance();
        }
        catch (InstantiationException | ClassNotFoundException | IllegalAccessException e )
        {
            e.printStackTrace();
        }
        dbName = name;
    }

    public static String getDBPathName() {
        return dbName;
    }
    public static synchronized AccountManagement getAccountManagement()
    {
        if ( accountManagement == null )
        {
            accountManagement = new AccountManagementHSQLDB( Services.getDBPathName() );
        }

        return accountManagement;
    }

    public static synchronized RecipeManagement getRecipeManagement()
    {
        if ( recipeManagement == null )
        {
            recipeManagement = new RecipeManagementHSQLDB(Services.getDBPathName());
        }

        return recipeManagement;
    }
}
