package GamesFX.games;

public enum TTTState {
    P1("Player1"),
    P2("Player2"),
    Tie("Tie"),
    Empty("empty");

    private String m_state;

    TTTState(String _state) {
        m_state = _state;
    }

    public static TTTState oppositePlayer(TTTState _state) {
        if (_state == TTTState.Empty)
            return null;
        return TTTState.P1 == _state ? TTTState.P2 : TTTState.P1;
    }

    @Override
    public String toString() {
        return m_state;
    }
}
