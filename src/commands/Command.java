package commands;

/**
 * Абстрактный класс команды
 */
public abstract class Command {
    protected CommandManager commandManager;
    private String helpText;
    private String key; // key for command hashmap repository

    /**
     *
     * @param key ключ команды
     * @param helpText описание команды
     */
    public Command(String key, String helpText) {
        this.key = key;
        this.helpText = helpText;
    }

    public abstract void execute(String[] args);

    public String getKey() {
        return key;
    }

    public String getHelpText() {
        return helpText;
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
}
