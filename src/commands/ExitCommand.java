package commands;

/**
 * Класс команды exit
 */
public class ExitCommand extends Command{
    public ExitCommand(String key, String helpText) {
        super(key, helpText);
    }

    @Override
    public void execute(String[] args) {
        commandManager.receiver.exit();
    }
}
