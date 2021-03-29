package commands;

/**
 * Класс команды info
 */
public class InfoCommand extends Command{
    public InfoCommand(String key, String helpText) {
        super(key, helpText);
    }
    @Override
    public void execute(String[] args) {
        commandManager.receiver.info();
    }
}
