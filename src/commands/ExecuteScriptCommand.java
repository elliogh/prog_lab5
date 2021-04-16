package commands;

/**
 * Класс команды execute_script
 */
public class ExecuteScriptCommand extends Command{
    public ExecuteScriptCommand(String key, String helpText) {
        super(key, helpText);
    }

    @Override
    public void execute(String[] args) {
        try {
            String filename = args[0];
            commandManager.receiver.executeScript(filename);
        } catch (Exception e) {
            System.out.println("Не было введено название файла");
        }
    }
}
