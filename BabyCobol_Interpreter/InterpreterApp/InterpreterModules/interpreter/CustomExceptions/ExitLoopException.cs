public class ExitLoopException : Exception
{
    // Constructor that takes a message
    public ExitLoopException(string message)
        : base(message)
    {
    }

    // Constructor that takes a message and an inner exception
    public ExitLoopException(string message, Exception inner)
        : base(message, inner)
    {
    }
}