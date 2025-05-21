using System;
public class NextSentenceException : Exception
{
    // Constructor that takes a message
    public NextSentenceException(string message)
        : base(message)
    {
    }

    // Constructor that takes a message and an inner exception
    public NextSentenceException(string message, Exception inner)
        : base(message, inner)
    {
    }
}