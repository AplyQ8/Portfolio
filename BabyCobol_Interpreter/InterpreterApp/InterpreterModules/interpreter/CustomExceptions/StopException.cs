using System;
public class StopException : Exception
{
    // Constructor that takes a message
    public StopException(string message)
        : base(message)
    {
    }

    // Constructor that takes a message and an inner exception
    public StopException(string message, Exception inner)
        : base(message, inner)
    {
    }
}