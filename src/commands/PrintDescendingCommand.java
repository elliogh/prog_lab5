package commands;

/**
 * Класс команды print_descending
 */
public class PrintDescendingCommand extends Command{
    public PrintDescendingCommand(String key, String helpText) {
        super(key, helpText);
    }

    @Override
    public void execute(String[] args) {
        commandManager.receiver.printDescending();
    }
}
