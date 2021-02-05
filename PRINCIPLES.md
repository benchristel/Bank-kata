# Principles

## Keeping options open

- don't throw away data needlessly (Consumer<Exception> instead of Consumer<String>)
- when in doubt, refuse the temptation to guess
- different edges of the hexagon should not know about each other (e.g. the ATM class doesn't talk to the repository)

## Legibility

- tests should assert on hardcoded primitives—strings, booleans, numbers. Custom value types impede legibility.
  When debugging an error message, programmers will see the primitives, not the custom type. It's nice if they
  can match the error to something in a test
- Use types to make it clear how responsibilities are assigned. If the account stores its transactions in an
  AppendOnlyList<Transaction>, you know validation is happening in the Account, not the AppendOnlyList. The
  AppendOnlyList is generic—it doesn't know anything about transactions, so can't validate them.
