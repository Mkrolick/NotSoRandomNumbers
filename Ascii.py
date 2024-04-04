class TreeNode:
    def __init__(self, val=None, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

def insert_level_order(arr, root, i, n):
    if i < n:
        temp = TreeNode(arr[i])
        root = temp
        root.left = insert_level_order(arr, root.left, 2 * i + 1, n)
        root.right = insert_level_order(arr, root.right, 2 * i + 2, n)
    return root

def get_max_depth(root):
    if root is None:
        return 0
    else:
        left_depth = get_max_depth(root.left)
        right_depth = get_max_depth(root.right)
        return max(left_depth, right_depth) + 1

def print_vertical_tree(root):
    if not root:
        return [""]
    
    depth = get_max_depth(root)
    width = (2 ** depth) - 1
    res = [[" " for _ in range(width)] for _ in range(depth)]
    
    def fill(pos, level, node):
        if node:
            middle = (pos[0] + pos[1]) // 2
            res[level][middle] = str(node.val)
            fill((pos[0], middle), level + 1, node.left)
            fill((middle + 1, pos[1]), level + 1, node.right)

    fill((0, width), 0, root)
    return [" ".join(row) + "\n" for row in res]

def create_ascii_trees_from_file(input_filename, output_filename):
    with open(input_filename, 'r') as file:
        lines = file.readlines()

    with open(output_filename, 'w') as file:
        for line in lines:
            line = line.strip()
            values = eval(line.split(' - ')[1].split(': ')[1])

            for i in range(2, len(values) + 1):
                subsection = values[:i]
                root = None
                root = insert_level_order(subsection, root, 0, len(subsection))
                tree_str = print_vertical_tree(root)
                file.write(f"ASCII tree for subsection {subsection}:\n")
                file.writelines(tree_str)
                file.write("-" * 50 + "\n")
            # Optionally, add a separator between trees from different lines
            file.write("=" * 100 + "\n\n")

create_ascii_trees_from_file('output.txt', 'tree.txt')
