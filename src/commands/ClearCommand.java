package commands;

/**
 * Класс команды clear
 */
public class ClearCommand extends Command{
    public ClearCommand(String key, String helpText) {
        super(key, helpText);
    }

    @Override
    public void execute(String[] args) {
        commandManager.receiver.clear();
    }
}
