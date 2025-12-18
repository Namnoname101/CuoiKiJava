import os
import sys

SRC_DIR = os.path.join(os.path.dirname(os.path.dirname(__file__)), 'src')


def strip_comments_java(src):
    out = []
    i = 0
    n = len(src)
    in_block = False
    in_line = False
    in_dquote = False
    in_squote = False
    while i < n:
        c = src[i]
        nxt = src[i+1] if i+1 < n else ''

        if in_block:
            if c == '*' and nxt == '/':
                in_block = False
                i += 2
                continue
            else:
                i += 1
                continue
        if in_line:
            if c == '\n':
                in_line = False
                out.append(c)
            i += 1
            continue
        if in_dquote:
            # handle escaped characters
            out.append(c)
            if c == '\\':
                if i+1 < n:
                    out.append(src[i+1])
                    i += 2
                    continue
            if c == '"':
                in_dquote = False
            i += 1
            continue
        if in_squote:
            out.append(c)
            if c == '\\':
                if i+1 < n:
                    out.append(src[i+1])
                    i += 2
                    continue
            if c == "'":
                in_squote = False
            i += 1
            continue

        # not in any special state
        if c == '/' and nxt == '*':
            in_block = True
            i += 2
            continue
        if c == '/' and nxt == '/':
            in_line = True
            i += 2
            continue
        if c == '"':
            in_dquote = True
            out.append(c)
            i += 1
            continue
        if c == "'":
            in_squote = True
            out.append(c)
            i += 1
            continue

        out.append(c)
        i += 1
    return ''.join(out)


def process_file(path):
    with open(path, 'r', encoding='utf-8') as f:
        src = f.read()
    new = strip_comments_java(src)
    if new != src:
        bak = path + '.bak'
        if not os.path.exists(bak):
            with open(bak, 'w', encoding='utf-8') as f:
                f.write(src)
        with open(path, 'w', encoding='utf-8') as f:
            f.write(new)
        print('Updated:', path)
    else:
        print('No change:', path)


if __name__ == '__main__':
    changed = 0
    for root, dirs, files in os.walk(SRC_DIR):
        for name in files:
            if name.endswith('.java'):
                path = os.path.join(root, name)
                process_file(path)
                changed += 1
    print('Processed', changed, 'Java files')
