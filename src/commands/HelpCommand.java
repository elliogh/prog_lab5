package commands;

/**
 * Класс команды help
 */
public class HelpCommand extends Command{
    public HelpCommand(String key, String helpText) {
        super(key, helpText);
    }

    @Override
    public void execute(String[] args) {
        commandManager.receiver.help();
    }
}
