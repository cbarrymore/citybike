namespace usuarios.Exceptions
{

    [Serializable]
    public class EntidadNoEncontradaException : Exception
    {
        public EntidadNoEncontradaException() { }

        public EntidadNoEncontradaException(string message)
            : base(message) { }

        public EntidadNoEncontradaException(string message, Exception inner)
            : base(message, inner) { }
    }

    
}