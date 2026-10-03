package org.tihrc.microj.compiler;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import org.tihrc.microj.antlr.MicroJLexer;
import org.tihrc.microj.antlr.MicroJParser;

import java.util.ArrayDeque;
import java.util.Deque;

public final class IndentingLexer extends MicroJLexer {
    private final Deque<Integer> indents = new ArrayDeque<>();
    private final Deque<Token> tokenQueue = new ArrayDeque<>();
    private Token lastToken = null;
    private int parenLevel = 0;

    public IndentingLexer(CharStream input) {
        super(input);
        indents.push(0);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public Token nextToken() {
        if (!tokenQueue.isEmpty()) {
            return tokenQueue.poll();
        }

        Token token = super.nextToken();

        if (token.getType() == LPAREN || token.getType() == LBRACKET || token.getType() == LBRACE) {
            parenLevel++;
        } else if (token.getType() == RPAREN || token.getType() == RBRACKET || token.getType() == RBRACE) {
            if (parenLevel > 0) parenLevel--;
        }

        if (token.getType() == Token.EOF) {
            if (lastToken != null && lastToken.getType() != NEWLINE) {
                tokenQueue.add(createToken(NEWLINE, token));
            }

            while (indents.peek() != null && indents.peek() > 0) {
                indents.pop();
                tokenQueue.add(createToken(MicroJParser.DEDENT, token));
            }

            tokenQueue.add(token);
            return tokenQueue.poll();
        }

        if (token.getType() != NEWLINE &&
                token.getType() != WS &&
                token.getType() != COMMENT) {
            lastToken = token;
        }

        if (parenLevel > 0) {
            if (token.getType() == NEWLINE ||
                    token.getType() == WS ||
                    token.getType() == COMMENT) {
                return nextToken();
            }
            return token;
        }

        if (token.getType() == NEWLINE &&
                lastToken != null &&
                lastToken.getType() != NEWLINE) {

            int indent = getIndentationLevel();

            if (indent == -1) {
                return nextToken();
            }

            tokenQueue.add(token);

            int prevIndent = indents.peek();

            if (indent > prevIndent) {
                indents.push(indent);
                tokenQueue.add(
                        createToken(MicroJParser.INDENT, token)
                );
            } else {
                while (indents.peek() != null &&
                        indent < indents.peek()) {

                    indents.pop();

                    tokenQueue.add(
                            createToken(MicroJParser.DEDENT, token)
                    );
                }
            }

            return tokenQueue.poll();
        }

        if (token.getType() == COMMENT) {
            return nextToken();
        }

        return token;
    }

    private int getIndentationLevel() {
        int indent = 0;
        int lookahead = 1;
        char c = (char) _input.LA(lookahead);

        while (c == ' ' || c == '\t') {
            if (c == ' ') indent++;
            else indent += 4;

            lookahead++;
            c = (char) _input.LA(lookahead);
        }

        if (c == '\n' ||
                c == '\r' ||
                c == (char) Token.EOF ||
                c == '#') {
            return -1;
        }

        return indent;
    }

    private Token createToken(int type, Token prototype) {
        CommonToken token = new CommonToken(
                _tokenFactorySourcePair,
                type,
                Token.DEFAULT_CHANNEL,
                prototype.getStartIndex(),
                prototype.getStopIndex()
        );

        token.setLine(prototype.getLine());
        token.setCharPositionInLine(
                prototype.getCharPositionInLine()
        );

        return token;
    }
}