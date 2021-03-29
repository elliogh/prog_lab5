package commands;

/**
 * Класс команды save
 */
public class SaveCommand extends Command{
    public SaveCommand(String key, String helpText) {
        super(key, helpText);
    }

    @Override
    public void execute(String[] args) {
        commandManager.receiver.save();
    }
}
